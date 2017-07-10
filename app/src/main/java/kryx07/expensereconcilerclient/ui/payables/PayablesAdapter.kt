package kryx07.expensereconcilerclient.ui.transactions

import android.arch.persistence.room.util.StringUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_payables_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.utils.StringUtilities
import timber.log.Timber

class PayablesAdapter(var currentUserName: String) : RecyclerView.Adapter<PayablesAdapter.PayablesHolder>() {

    var payables = Payables(mutableListOf<Payable>())

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PayablesHolder {
//        Timber.plant(Timber.DebugTree())
        Timber.e("onCreateViewHolder")
        return PayablesHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_payables_adapter, parent, false), currentUserName)
    }

    override fun onBindViewHolder(holder: PayablesHolder?, position: Int) {
        Timber.e("onBindViewHolder")
        holder?.setupItem(payables.payables[position])
    }

    override fun getItemCount(): Int = payables.payables.size

    class PayablesHolder(itemView: View, val currentUserId: String) : RecyclerView.ViewHolder(itemView) {

        fun setupItem(payable: Payable) {
            Timber.e("setupTransaction")
            //var currentUser = Users(HashSet<User>()).getByUserName(currentUserName)

            //itemView.amount.text = "%.2f".format(payable.amount) + " " + itemView.context.getString(R.string.currency)
            itemView.amount.text = StringUtilities.formatCurrency(payable.amount, itemView.context.getString(R.string.currency))
            if (payable.debtor.userName.toString() == currentUserId) {
                itemView.type.text = "Payable"
                //itemView.type.setTextColor(Color.RED)
                itemView.direction.text = " -> "
                itemView.another_person.text = payable.payer.userName
                itemView.amount.setTextColor(Color.RED)

            } else if (payable.payer.userName.toString() == currentUserId) {
                itemView.type.text = "Receivable"
//                itemView.type.setTextColor(Color.GREEN)
                itemView.direction.text = " <- "
                itemView.another_person.text = payable.debtor.userName
                itemView.amount.setTextColor(Color.GREEN)
            }

        }

    }

    fun updateData(payables: Payables) {
        Timber.e("update Data: " + payables)

        this.payables.clear()
        this.payables.addAll(payables)
        notifyDataSetChanged()
    }
}