package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class PayablesPresenter @Inject constructor(var apiClient: ApiClient, var context: Context, val sharedprefs: SharedPreferencesManager) {

    private var view: PayablesMvpView? = null

    fun attach(payablesMvpView: PayablesMvpView) {
        this.view = payablesMvpView
        Timber.plant(Timber.DebugTree())
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
            }

            override fun onFailure(call: Call<Payables>?, t: Throwable?) {
                Timber.e(context.getString(R.string.fetching_error))

            }

        })
    }

    /* fun getAllTransactions(): Transactions? {
         var payables: Transactions? = null
         apiClient.service.payables.enqueue(object : Callback<Transactions> {
             override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
                 if (response!!.isSuccessful) {
                     payables = response.body()
                 }
             }

             override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
                 Timber.e(context.getString(R.string.fetching_error))
             }

         })

         return payables
     }*/
}