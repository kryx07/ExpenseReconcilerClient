package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.model.transactions.Payables
import kryx07.expensereconcilerclient.model.transactions.Transactions

interface PayablesMvpView {

    fun updateData(payables: Payables)
}