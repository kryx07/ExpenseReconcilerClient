package kryx07.expensereconcilerclient.model.transactions

import java.io.Serializable


data class Payables(var payables: MutableList<Payable>) : Serializable {
    fun clear() = this.payables.clear()
    fun addAll(payables: MutableList<Payable>) = this.payables.addAll(payables)
    fun addAll(payables: Payables) = this.payables.addAll(payables.payables)
}


