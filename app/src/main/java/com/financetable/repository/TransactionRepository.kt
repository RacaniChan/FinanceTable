package com.financetable.repository

import com.financetable.data.Transaction
import com.financetable.data.TransactionDao
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class TransactionRepository(private val dao: TransactionDao) {
    fun observeAll(): Flow<List<Transaction>> = dao.observeAll()

    suspend fun insert(tx: Transaction): Long = dao.insert(tx)

    suspend fun delete(tx: Transaction) = dao.delete(tx)

    suspend fun getTotals(): Pair<Long, Long> {
        val inc = dao.sumIncome() ?: 0L
        val exp = dao.sumExpense() ?: 0L
        return Pair(inc, exp)
    }
}
