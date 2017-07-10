package kryx07.expensereconcilerclient.ui.payables

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_payables.*
import kotlinx.android.synthetic.main.fragment_payables.view.*
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.model.transactions.Transactions
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.network.ApiService
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.*
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.StringUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject


class PayablesFragment : Fragment(), PayablesMvpView {


    val TAG: String = this::class.java.javaClass.name


    fun newInstance(id: Int): PayablesFragment {
        // We cannot use custom constructor - this is a way to pass some data to a fragment
        val args = Bundle()
        args.putInt(TAG, id)
        val fragment = PayablesFragment()
        fragment.arguments = args
        return fragment
    }

    @Inject lateinit var presenter: PayablesPresenter
    @Inject lateinit var sharedPrefs: SharedPreferencesManager
    var adapter: PayablesAdapter? = null
    lateinit var myUserName: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_payables, container, false)
        ButterKnife.bind(this, view)
        Timber.plant(Timber.DebugTree())
        App.appComponent.inject(this)

        this.myUserName = sharedPrefs.read(getString(kryx07.expensereconcilerclient.R.string.my_user))
        this.adapter = PayablesAdapter(myUserName)
        view.payables_recycler.layoutManager = LinearLayoutManager(context)
        view.payables_recycler.adapter = adapter

        presenter.attach(this)

        (activity as DashboardActivity).supportActionBar?.setTitle(R.string.payables)

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

    override fun updateData(payables: Payables) {
        adapter?.updateData(payables)
    }

    override fun updateTotals(receivablesTotal: BigDecimal, payablesTotal: BigDecimal) {
        payables_total_amount.text = StringUtilities.formatCurrency(payablesTotal, getString(R.string.currency))
        receivables_total_amount.text = StringUtilities.formatCurrency(receivablesTotal, getString(R.string.currency))
        payables_total_amount.setTextColor(Color.RED)
        receivables_total_amount.setTextColor(Color.GREEN)
    }
    /*private fun setTotals(allPayables: Payables) {
        var myPayables = BigDecimal(0)
        var myReceivables = BigDecimal(0)

        allPayables.payables.forEach { (debtor, payer, amount) ->
            if (debtor.userName == myUserName) {
                myReceivables = myReceivables.add(amount)
            } else if (payer.userName == myUserName) {
                myPayables = myPayables.add(amount)
            }
        }

        payables_total_amount.text = StringUtilities.formatCurrency(myPayables, getString(R.string.currency))
        receivables_total_amount.text = StringUtilities.formatCurrency(myReceivables, getString(R.string.currency))
        payables_total_amount.setTextColor(Color.RED)
        receivables_total_amount.setTextColor(Color.GREEN)
    }
*/
}