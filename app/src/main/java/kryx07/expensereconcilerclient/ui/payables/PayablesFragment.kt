package kryx07.expensereconcilerclient.ui.payables

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.network.ApiService
import kryx07.expensereconcilerclient.ui.transactions.TransactionsPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject


class PayablesFragment : Fragment() {

    @Inject lateinit var apiClient: ApiClient

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_payables, container, false)
        ButterKnife.bind(this, view)
        Timber.plant(Timber.DebugTree())
        (activity.application as App).appComponent?.inject(this)

        apiClient.service.payables.enqueue(object : Callback<Payables> {

            override fun onResponse(call: Call<Payables>?, response: Response<Payables>?) {
                Timber.e(response!!.body().toString())

            }

            override fun onFailure(call: Call<Payables>?, t: Throwable?) {
                Timber.e(getString(R.string.fetching_error))
            }

        })

        return view
    }

    /*   Timber.e(apiClient.javaClass.toString())

       apiClient.service.users.enqueue(
       object : Callback<Users> {
           override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
               if (response!!.isSuccessful) {
                   Timber.e(response.body().toString())
               }
           }

           override fun onFailure(call: Call<Users>?, t: Throwable?) {
               Timber.e(getString(R.string.fetching_error))
           }

       })

       fetchTransactions()
       addPerson("dupa", "dupa2")
       getAllPeople()
   }

   @OnClick(R.id.test_button)
   fun fetchTransactions() {
       apiClient.service.transactions.enqueue(object : Callback<Transactions> {

           override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
               if (response!!.isSuccessful) {
                   Timber.e(response.body().toString())
                   val transactions: Transactions = Transactions(response.body().transactions)
                   Timber.e(transactions.toString())
               }
           }

           override fun onFailure(call: Call<Transactions>?, t: Throwable?) {
               Timber.e(getString(R.string.fetching_error))
               Timber.e(t?.message ?: "no message")
           }
       })


   }


   fun addPerson(firstName: String, lastName: String) {
       val person: Person = Person(0, firstName, lastName)

       App.database.personDao().insert(person)

       *//*   Single.fromCallable {
           App.database.personDao().insert(person)
       }.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()).subscribe()*//*
}
*//*
    fun registerAllPersonListener() {

        App.database?.personDao()?.getAllPeople()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfPeople ->
                    view.personTableUpdated(listOfPeople)
                }
    }*//*

fun getAllPeople() {
    Timber.e(App.database.personDao().getAllPeople().toString())
}*/
}