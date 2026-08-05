package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationItem(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val type: String, // DEPOSIT, WITHDRAWAL, REFERRAL, SALARY, SYSTEM, SECURITY
    val isRead: Boolean = false
)
