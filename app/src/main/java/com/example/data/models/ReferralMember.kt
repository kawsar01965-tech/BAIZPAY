package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "referral_members")
data class ReferralMember(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val level: Int, // 1 to 5
    val joinedDate: String,
    val isVerified: Boolean,
    val totalEarnedFromMember: Double,
    val directTeamCount: Int = 0
)
