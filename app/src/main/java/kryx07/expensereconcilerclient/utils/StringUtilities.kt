package kryx07.expensereconcilerclient.utils

import java.math.BigDecimal
import java.text.NumberFormat

object StringUtilities {

    fun formatCurrency(number: BigDecimal, currency: String): String {
        return NumberFormat.getCurrencyInstance().format(number) + " " + currency
    }
}
