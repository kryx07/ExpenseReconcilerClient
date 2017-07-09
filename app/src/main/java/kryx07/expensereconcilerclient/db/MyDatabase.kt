package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import kryx07.expensereconcilerclient.model.transactions.Transaction

@Database(entities = arrayOf(Transaction::class), version = 4)
@TypeConverters(*arrayOf(Converters::class))
abstract class MyDatabase : RoomDatabase() {
    //    abstract fun personDao(): PersonDao
    abstract fun transactionDao(): TransactionDao
}

