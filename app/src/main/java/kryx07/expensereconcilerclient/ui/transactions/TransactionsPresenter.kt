package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(var apiClient: ApiClient, var context: Context) {

    private var view: TransactionsMvpView? = null

    fun attach(transactionsMvpView: TransactionsMvpView) {
        this.view = transactionsMvpView
        Timber.plant(Timber.DebugTree())
    }


    fun detach() {
        this.view = null
    }

    fun start() {
        apiClient.service.transactions.enqueue(object : Callback<Transactions> {
            override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
                if (response!!.isSuccessful) {
                    Timber.e(response.body().toString())
                    val transactions = response.body()
                    view?.updateData(transactions)
                }
            }

            override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
                Timber.e(context.getString(R.string.fetching_error))
            }

        })
    }

   /* fun getAllTransactions(): Transactions? {
        var transactions: Transactions? = null
        apiClient.service.transactions.enqueue(object : Callback<Transactions> {
            override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
                if (response!!.isSuccessful) {
                    transactions = response.body()
                }
            }

            override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
                Timber.e(context.getString(R.string.fetching_error))
            }

        })

        return transactions
    }*/
}