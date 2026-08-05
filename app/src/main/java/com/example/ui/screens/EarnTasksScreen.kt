package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.TaskItem
import com.example.ui.components.GlassCard
import com.example.ui.components.ScratchCardComponent
import com.example.ui.components.SpinWheelComponent
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun EarnTasksScreen(
    tasks: List<TaskItem>,
    onClaimTask: (TaskItem) -> Unit,
    onSpinWin: (Double) -> Unit,
    onScratchWin: (Double) -> Unit
) {
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }

    val filteredTasks = tasks.filter { task ->
        when (selectedCategoryFilter) {
            "ALL" -> true
            "DAILY" -> task.category == "DAILY"
            "SURVEY" -> task.category == "SURVEY"
            "VIDEO" -> task.category == "VIDEO"
            "QUIZ" -> task.category == "QUIZ"
            else -> true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            Column {
                Text(
                    text = "Task & Reward Center",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold
                )
                Text(
                    text = "Complete daily tasks, spin the wheel & scratch cards for instant earnings",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        // Spin Wheel Section
        item {
            SpinWheelComponent(onWinReward = onSpinWin)
        }

        // Scratch Card Section
        item {
            ScratchCardComponent(onScratchComplete = onScratchWin)
        }

        // Task Center Categories Filter
        item {
            Column {
                Text(
                    text = "Daily & Weekly Earn Tasks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("ALL", "DAILY", "SURVEY", "VIDEO", "QUIZ").forEach { filter ->
                        val isSelected = selectedCategoryFilter == filter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) RoyalBluePrimary else DarkNavyCard)
                                .border(1.dp, if (isSelected) BrightGold else GlassBorderGold, RoundedCornerShape(16.dp))
                                .clickable { selectedCategoryFilter = filter }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) BrightGold else Color.Gray,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        items(filteredTasks) { task ->
            TaskRowItem(task, onClaimTask)
        }
    }
}

@Composable
fun TaskRowItem(task: TaskItem, onClaimTask: (TaskItem) -> Unit) {
    GlassCard(
        cornerRadius = 16.dp,
        borderColor = if (task.isCompleted) SuccessGreen.copy(alpha = 0.5f) else GlassBorderGold,
        contentPadding = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                val icon = when (task.category) {
                    "VIDEO" -> Icons.Default.Videocam
                    "QUIZ" -> Icons.Default.Quiz
                    "SURVEY" -> Icons.Default.CheckCircle
                    else -> Icons.Default.Star
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(RoyalBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = task.title, tint = BrightGold, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+$${String.format("%.2f", task.rewardAmount)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SuccessGreen,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { if (!task.isCompleted) onClaimTask(task) },
                    enabled = !task.isCompleted,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrightGold,
                        contentColor = Color.Black,
                        disabledContainerColor = DarkNavyCard,
                        disabledContentColor = SuccessGreen
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (task.isCompleted) "Claimed" else "Claim",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
