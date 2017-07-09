package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_payables.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.StringUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class PayablesPresenter @Inject constructor(var apiClient: ApiClient, var context: Context, val sharedprefs: SharedPreferencesManager) {

    private var view: PayablesMvpView? = null

    lateinit var database: MyDatabase

    fun attach(payablesMvpView: PayablesMvpView) {
        this.view = payablesMvpView
        Timber.plant(Timber.DebugTree())
        this.database = App.database
    }

    fun detach() {
        this.view = null
    }

    fun start() {
        apiClient.service.getPayables(sharedprefs.read(context.getString(R.string.my_user))).enqueue(object : Callback<Payables> {

            override fun onResponse(call: Call<Payables>?, response: Response<Payables>?) {
                Timber.e(response!!.body().toString())
                val payables = response.body()
                view?.updateData(payables)
                setTotals(payables)
                payables.payables.forEach { p ->
                    database.payablesDao().insert(p)
                }
                Timber.e("Read from db: " + database.payablesDao().getAll().toString())
            }

            override fun onFailure(call: Call<Payables>?, t: Throwable?) {
                Timber.e(context.getString(R.string.fetching_error))

            }

        })
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


}