package com.financetable.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tx: Transaction): Long

    @Delete
    suspend fun delete(tx: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 1")
    suspend fun sumIncome(): Long?

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 0")
    suspend fun sumExpense(): Long?
}
