package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import kryx07.expensereconcilerclient.model.persontest.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun getAllPeople(): Flowable<List<Person>>

    @Insert
    fun insert(person: Person)
}
