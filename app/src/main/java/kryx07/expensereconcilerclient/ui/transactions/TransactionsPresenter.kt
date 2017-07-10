package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import android.widget.Toast
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.events.ShowRefresher
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(var apiClient: ApiClient,
                                                var context: Context,
                                                val sharedPrefs: SharedPreferencesManager,
                                                val database: MyDatabase) {


    private var view: TransactionsMvpView? = null


    fun attach(transactionsMvpView: TransactionsMvpView) {
        this.view = transactionsMvpView
//        Timber.plant(Timber.DebugTree())
        Timber.wtf("Registering Presenter as EventBus Subscriber")
        EventBus.getDefault().register(this)
    }


    fun detach() {
        this.view = null
    }

    fun start() {
        requestTransactions()
    }

    private fun requestTransactions() {
        showProgress()

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
                        hideProgress()
                    }

                    override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
                        showErrorMessage()
                        hideProgress()
                    }
                })
    }

    @Subscribe()
    fun onRefresh(showRefresher: ShowRefresher) {
        if (view != null) {
            if (showRefresher.fragmentTAG == view!!.provideTAG())

                Timber.e("onRefresh received")
            requestTransactions()
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

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }

}