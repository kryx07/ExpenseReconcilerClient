package kryx07.expensereconcilerclient.model

import java.io.Serializable

data class User(var userName: String) : Serializable {

    private val serialVersionUID = 53877953648246L

    //var userName: String? = null

    var errorMessage: String? = null

}