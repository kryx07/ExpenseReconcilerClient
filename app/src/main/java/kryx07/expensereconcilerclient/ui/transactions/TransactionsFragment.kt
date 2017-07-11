package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.ui.DashboardActivity
import javax.inject.Inject

class TransactionsFragment : Fragment(), TransactionsMvpView {
   /* override fun provideTAG(): String {
        return TAG
    }*/

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
    var adapter: TransactionsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)

        //Adapter setup
        adapter = TransactionsAdapter()
        view.transactions_recycler.layoutManager = LinearLayoutManager(context)
        view.transactions_recycler.adapter = adapter

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

    override fun updateData(transactions: Transactions) {
        adapter?.updateData(transactions)
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
       apiClient.service.payables.enqueue(object : Callback<Transactions> {

           override fun onResponse(call: Call<Transactions>?, response: Response<Transactions>?) {
               if (response!!.isSuccessful) {
                   Timber.e(response.body().toString())
                   val payables: Transactions = Transactions(response.body().payables)
                   Timber.e(payables.toString())
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

