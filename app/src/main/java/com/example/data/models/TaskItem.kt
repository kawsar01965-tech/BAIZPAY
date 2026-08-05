package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String, // DAILY, WEEKLY, MONTHLY, SURVEY, VIDEO, ARTICLE, QUIZ, SOCIAL, SPIN, SCRATCH
    val rewardAmount: Double,
    val progress: Int = 0,
    val totalSteps: Int = 1,
    val isCompleted: Boolean = false,
    val iconName: String = "star"
)
