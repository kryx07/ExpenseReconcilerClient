package kryx07.expensereconcilerclient.model.transactions

import java.io.Serializable


data class Transactions(var transactions: MutableList<Transaction>) : Serializable {

    fun clear() {
        transactions = mutableListOf<Transaction>()
    }

    fun addAll(transactions: List<Transaction>) {
        this.transactions.addAll(transactions)
    }

    fun addAll(transactions: Transactions) {
        this.transactions.addAll(transactions.transactions)
    }
}