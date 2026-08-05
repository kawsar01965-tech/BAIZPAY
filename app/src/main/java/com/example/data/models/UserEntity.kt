package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String = "USR_883921",
    val fullName: String = "Alexander Vance",
    val firstName: String = "Alexander",
    val lastName: String = "Vance",
    val username: String = "alex_vance",
    val email: String = "a.vance@baizpay.com",
    val authProvider: String = "EMAIL", // EMAIL, GOOGLE, FACEBOOK
    val phoneNumber: String = "+1 (555) 234-8900",
    val isAccountVerified: Boolean = true, // USD $5 account verification status
    val kycStatus: String = "VERIFIED", // UNVERIFIED, PENDING, VERIFIED
    val twoFactorEnabled: Boolean = true,
    val referralCode: String = "BAIZ-VANCE-99",
    val referredBy: String? = "BAIZ-GLOBAL-01",
    val currentSalaryRank: String = "Crown", // Bronze to Global Ambassador
    val directReferralsCount: Int = 1240,
    val totalTeamCount: Int = 8940,
    val level1Count: Int = 1240,
    val level2Count: Int = 2850,
    val level3Count: Int = 2410,
    val level4Count: Int = 1520,
    val level5Count: Int = 920,
    val avatarUrl: String = "",
    val isAdmin: Boolean = false
)
