package kryx07.expensereconcilerclient.ui.payables

import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.PayablesMvpView
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class PayablesPresenter @Inject constructor(var apiClient: ApiClient,
                                            var context: Context,
                                            val sharedprefs: SharedPreferencesManager,
                                            val database: MyDatabase) : BasePresenter<PayablesMvpView>() {

    fun start() {
        requestPayables()
    }

    fun requestPayables() {
        showProgress()

        val payablesObservable: Observable<Payables>? = apiClient.service.getPayables(sharedprefs.read("MY_USER"))
        payablesObservable!!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    payables ->
                    Timber.e(payables.toString())
                    view.updateData(payables)
                    setTotals(payables)
                    payables.payables.forEach { p ->
                        database.payablesDao().insert(p)
                    }
                    Timber.e("Read from db: " + database.payablesDao().getAll().toString())
                    hideProgress()

                },{
                    showErrorMessage()
                    hideProgress()
                })

    }

    private fun showProgress() {
        EventBus.getDefault().post(ShowProgress())
    }

    private fun hideProgress() {
        EventBus.getDefault().post(HideProgress())
        EventBus.getDefault().post(HideRefresher())
    }

    private fun setTotals(allPayables: Payables) {
        var myPayables = BigDecimal(0)
        var myReceivables = BigDecimal(0)
        val myUserName = sharedprefs.read(context.getString(R.string.my_user))

        allPayables.payables.forEach { (id, debtor, payer, amount) ->
            if (debtor.userName == myUserName) {
                myReceivables = myReceivables.add(amount)
            } else if (payer.userName == myUserName) {
                myPayables = myPayables.add(amount)
            }
        }

        view.updateTotals(myReceivables, myPayables)
    }

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }

}