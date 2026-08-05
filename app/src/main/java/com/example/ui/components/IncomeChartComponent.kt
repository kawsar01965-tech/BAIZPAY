package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderGold
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun IncomeChartComponent() {
    var selectedFilter by remember { mutableStateOf("Weekly") }

    val pointsData = when (selectedFilter) {
        "Daily" -> listOf(120f, 250f, 180f, 320f, 410f, 290f, 580f)
        "Weekly" -> listOf(1450f, 2100f, 1950f, 3200f, 2800f, 4100f, 5200f)
        else -> listOf(8500f, 12400f, 11000f, 16800f, 21500f, 28900f, 35000f)
    }

    val labels = when (selectedFilter) {
        "Daily" -> listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        "Weekly" -> listOf("W1", "W2", "W3", "W4", "W5", "W6", "W7")
        else -> listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
    }

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = GlassBorderGold
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Income Analytics Growth",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "+24.8% vs last period",
                        style = MaterialTheme.typography.labelSmall,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkNavyCard)
                        .border(1.dp, GlassBorderGold, RoundedCornerShape(20.dp))
                        .padding(4.dp)
                ) {
                    listOf("Daily", "Weekly", "Monthly").forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Text(
                            text = filter,
                            color = if (isSelected) BrightGold else Color.Gray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) RoyalBluePrimary else Color.Transparent)
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Canvas Bar & Line Chart
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                val width = size.width
                val height = size.height
                val maxVal = pointsData.maxOrNull() ?: 1000f
                val spacing = width / (pointsData.size - 1)

                // Render Background Gradient Bars
                val barWidth = 16.dp.toPx()
                pointsData.forEachIndexed { index, value ->
                    val x = index * spacing
                    val barHeight = (value / maxVal) * (height * 0.8f)
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(BrightGold.copy(alpha = 0.6f), RoyalBluePrimary.copy(alpha = 0.2f))
                        ),
                        topLeft = Offset(x - barWidth / 2f, height - barHeight),
                        size = Size(barWidth, barHeight)
                    )
                }

                // Smooth Trend Line
                val path = Path()
                pointsData.forEachIndexed { index, value ->
                    val x = index * spacing
                    val y = height - (value / maxVal) * (height * 0.8f)
                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        val prevX = (index - 1) * spacing
                        val prevY = height - (pointsData[index - 1] / maxVal) * (height * 0.8f)
                        val controlX1 = prevX + spacing / 2f
                        val controlX2 = x - spacing / 2f
                        path.cubicTo(controlX1, prevY, controlX2, y, x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = BrightGold,
                    style = Stroke(width = 4.dp.toPx())
                )

                // Point Dots
                pointsData.forEachIndexed { index, value ->
                    val x = index * spacing
                    val y = height - (value / maxVal) * (height * 0.8f)
                    drawCircle(color = BrightGold, radius = 5.dp.toPx(), center = Offset(x, y))
                    drawCircle(color = DarkNavyCard, radius = 2.dp.toPx(), center = Offset(x, y))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                labels.forEach { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
