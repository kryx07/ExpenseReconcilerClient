package kryx07.expensereconcilerclient.model.transactions

import java.io.Serializable


data class Transactions(var transactions: List<Transaction>) : Serializable {
}