package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(var apiClient: ApiClient, var context: Context, val sharedPrefs: SharedPreferencesManager) {

    private var view: TransactionsMvpView? = null
    lateinit var database: MyDatabase

    fun attach(transactionsMvpView: TransactionsMvpView) {
        this.view = transactionsMvpView
        Timber.plant(Timber.DebugTree())
        database = App.database
    }


    fun detach() {
        this.view = null
    }

    fun start() {

        apiClient.service.getTransactions(sharedPrefs.read(context.getString(R.string.my_user)))
                .enqueue(object : Callback<Transactions> {
                    override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
                        if (response!!.isSuccessful) {
                            Timber.e(response.body().toString())
                            val transactions = response.body()
                            view?.updateData(transactions)

                            for (transaction in transactions.transactions) {
                                database.transactionDao().insert(transaction)
                            }
                            Timber.e("Read from db: " + database.transactionDao().getAll().toString())
                        }
                    }

                    override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
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