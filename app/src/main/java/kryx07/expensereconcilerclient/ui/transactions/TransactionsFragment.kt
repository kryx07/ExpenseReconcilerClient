package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.RefreshableFragment
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.ui.DashboardActivity
import javax.inject.Inject

class TransactionsFragment : RefreshableFragment(), TransactionsMvpView {

    @Inject lateinit var presenter: TransactionsPresenter
    lateinit var adapter: TransactionsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        super.onCreateView(inflater, view.transactions_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)

        //Adapter setup
        adapter = TransactionsAdapter()
        view.transactions_recycler.layoutManager = LinearLayoutManager(context)
        view.transactions_recycler.adapter = adapter

        presenter.attachView(this)

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
        adapter.updateData(transactions)
    }

    override fun onRefresh() {
        presenter.requestTransactions()
    }


}

