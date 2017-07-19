package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import kryx07.expensereconcilerclient.model.transactions.Payable

@Dao
interface PayablesDao {

    @Query("SELECT * FROM Payables")
    fun getAll(): Flowable<List<Payable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(payable: Payable)
}
