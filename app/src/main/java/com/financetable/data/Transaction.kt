package com.financetable.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val amount: Long,
    val type: Boolean,
    val timestamp: Long,
    val locationName: String?,
    val latitude: Double?,
    val longitude: Double?
)
