package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.Refreshable
import kryx07.expensereconcilerclient.model.transactions.Transactions

interface TransactionsMvpView : Refreshable {

    fun updateData(transactions: Transactions)

    fun getItemCount(): Int

}