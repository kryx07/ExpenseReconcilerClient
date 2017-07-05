package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kryx07.expensereconcilerclient.model.persontest.Person

@Database(entities = arrayOf(Person::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
