package com.financetable.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financetable.data.Transaction
import com.financetable.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repo: TransactionRepository
) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = repo.observeAll()
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addTransaction(
        description: String,
        amountCents: Long,
        isIncome: Boolean,
        timestamp: Long,
        locationName: String?
    ) {
        viewModelScope.launch {
            val tx = Transaction(
                description = description,
                amount = amountCents,
                type = isIncome,
                timestamp = timestamp,
                locationName = locationName,
                latitude = null,
                longitude = null
            )
            repo.insert(tx)
        }
    }
}
