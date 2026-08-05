package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_balances")
data class WalletBalances(
    @PrimaryKey val id: Int = 1,
    val totalBalance: Double = 12480.50,
    val availableBalance: Double = 9850.20,
    val pendingBalance: Double = 1230.30,
    val referralIncome: Double = 4250.00,
    val salaryIncome: Double = 3500.00,
    val taskIncome: Double = 840.50,
    val marketplaceIncome: Double = 1120.00,
    val depositBalance: Double = 2000.00,
    val withdrawableBalance: Double = 8600.00,
    val bonusBalance: Double = 450.00,
    val cashbackBalance: Double = 210.00
)
