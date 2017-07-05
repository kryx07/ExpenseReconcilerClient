package kryx07.expensereconcilerclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kryx07.expensereconcilerclient.model.persontest.Person
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.model.users.Users
import kryx07.expensereconcilerclient.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Timber.plant(Timber.DebugTree())
        (application as App).appComponent?.inject(this)


        Timber.e(apiClient.javaClass.toString())

        apiClient.service.users.enqueue(object : Callback<Users> {
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

        Single.fromCallable {
            App.database.personDao().insert(person)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }
/*
    fun registerAllPersonListener() {

        App.database?.personDao()?.getAllPeople()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfPeople ->
                    view.personTableUpdated(listOfPeople)
                }
    }*/

}
