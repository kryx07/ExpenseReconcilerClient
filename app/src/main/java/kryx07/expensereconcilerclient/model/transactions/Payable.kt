package kryx07.expensereconcilerclient.model.transactions

import kryx07.expensereconcilerclient.model.users.User
import java.io.Serializable
import java.math.BigDecimal

data class Payable(var payer: User, var debtor: User, var amount: BigDecimal) : Serializable

