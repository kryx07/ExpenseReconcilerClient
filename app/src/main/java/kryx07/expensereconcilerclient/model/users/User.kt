package kryx07.expensereconcilerclient.model.users

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class User(@PrimaryKey var userName: String) : Serializable {

    @Ignore
    private val serialVersionUID = 53877953648246L

    //var userName: String? = null
    @Ignore
    var errorMessage: String? = null

}