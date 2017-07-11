package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.Refreshable
import kryx07.expensereconcilerclient.model.transactions.Payables
import java.math.BigDecimal

interface PayablesMvpView : Refreshable {

    fun updateData(payables: Payables)
    fun updateTotals(receivablesTotal:BigDecimal,payablesTotal:BigDecimal)
}