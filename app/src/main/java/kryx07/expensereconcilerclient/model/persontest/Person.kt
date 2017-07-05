package kryx07.expensereconcilerclient.model.persontest

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Person(
        @PrimaryKey(autoGenerate = true)
        var uid: Long,
        val firstName: String = "",
        val lastName: String = ""
)
