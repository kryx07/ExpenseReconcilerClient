package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import android.widget.Toast
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.base.MvpView
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.events.ShowRefresher
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class PayablesPresenter @Inject constructor(var apiClient: ApiClient,
                                            var context: Context,
                                            val sharedprefs: SharedPreferencesManager,
                                            val database: MyDatabase) : BasePresenter<PayablesMvpView>() {

    //override var view: PayablesMvpView
    override fun attachView(view: PayablesMvpView) {
        super.attachView(view)
        EventBus.getDefault().register(this)
    }

    override fun detach() {
        EventBus.getDefault().unregister(this)
        super.detach()
    }

    fun start() {
        requestPayables()
    }

    private fun requestPayables() {
        showProgress()

        apiClient.service.getPayables(sharedprefs.read(context.getString(R.string.my_user))).enqueue(object : Callback<Payables> {

            override fun onResponse(call: Call<Payables>?, response: Response<Payables>?) {
                if (response!!.isSuccessful) {
                    Timber.e(response.body().toString())
                    val payables = response.body()
                    view?.updateData(payables)
                    setTotals(payables)
                    payables.payables.forEach { p ->
                        database.payablesDao().insert(p)
                    }
                    Timber.e("Read from db: " + database.payablesDao().getAll().toString())
                }
                hideProgress()
            }

            override fun onFailure(call: Call<Payables>?, t: Throwable?) {
                showErrorMessage()
                hideProgress()
            }

        })
    }

    @Subscribe()
    fun onRefresh(showRefresher: ShowRefresher) {
        if (view != null) {
            if (showRefresher.fragment == view)

                Timber.e("onRefresh received")
            requestPayables()
        } else {
            Timber.e("View is null")
        }

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

        view?.updateTotals(myReceivables, myPayables)
    }

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }

}