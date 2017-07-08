package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.model.transactions.Transactions

interface TransactionsMvpView {

    fun updateData(transactions: Transactions)
}