package kryx07.expensereconcilerclient.model.transactions


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kryx07.expensereconcilerclient.model.users.User
import kryx07.expensereconcilerclient.model.users.Users
import org.joda.time.LocalDate

import java.io.Serializable
import java.math.BigDecimal

class Transaction :  Serializable {

    @PrimaryKey var id: String? = null
    var amount: BigDecimal? = null
    var description: String? = null
    var date: LocalDate? = null
    var common: Boolean? = null
    var payer: User? = null
    var users: Users? = null
    var payables: Payables? = null

    override fun toString(): String {
        return "Transaction(id=$id, amount=$amount, description=$description, date=$date, common=$common, payer=$payer, users=$users, payables=$payables)"
    }


}
