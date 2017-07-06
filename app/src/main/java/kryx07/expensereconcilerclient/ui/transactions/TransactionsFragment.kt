package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_transactions.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.ui.DashboardActivity
import timber.log.Timber
import javax.inject.Inject

class TransactionsFragment : Fragment(), TransactionsMvpView {

    val TAG: String = this::class.java.javaClass.name

    fun newInstance(id: Int): TransactionsFragment {
        // We cannot use custom constructor - this is a way to pass some data to a fragment
        val args = Bundle()
        args.putInt(TAG, id)
        val fragment = TransactionsFragment()
        fragment.arguments = args
        return fragment
    }

    @Inject lateinit var presenter: TransactionsPresenter

    var transactionsAdapter: TransactionsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        ButterKnife.bind(this, view)
        Timber.plant(Timber.DebugTree())
        (activity.application as App).appComponent?.inject(this)

        //Adapter setup
        transactionsAdapter = TransactionsAdapter()
        transactions_recycler.layoutManager = LinearLayoutManager(activity);
        transactions_recycler.adapter = transactionsAdapter;

        //TODO("to be implemented")
        presenter.attach(this)

        (activity as DashboardActivity).supportActionBar?.setTitle(R.string.transactions)
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    override fun updateData() {
        transactionsAdapter?.updateData(presenter.getAllTransactions())
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

