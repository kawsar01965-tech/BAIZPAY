package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val category: String, // DEPOSIT, WITHDRAWAL, REFERRAL_L1, REFERRAL_L2, REFERRAL_L3, REFERRAL_L4, REFERRAL_L5, SALARY, TASK, MARKETPLACE, BONUS, CASHBACK
    val status: String, // COMPLETED, PENDING, FAILED
    val timestamp: Long = System.currentTimeMillis(),
    val referenceNumber: String,
    val notes: String = ""
)
