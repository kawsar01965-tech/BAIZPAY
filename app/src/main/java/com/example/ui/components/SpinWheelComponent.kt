package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBlueDark
import com.example.ui.theme.RoyalBluePrimary
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpinWheelComponent(
    onWinReward: (Double) -> Unit
) {
    val sectorRewards = listOf(5.0, 10.0, 25.0, 50.0, 2.0, 100.0)
    val sectorColors = listOf(
        RoyalBluePrimary, MetallicGold, RoyalBlueDark, BrightGold, RoyalBluePrimary, MetallicGold
    )
    val sectorAngle = 360f / sectorRewards.size

    val rotationAnim = remember { Animatable(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = BrightGold
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "🎡 BaizPay Lucky Spin Wheel",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BrightGold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                // Wheel Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val diameter = size.minDimension
                    val radius = diameter / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)

                    rotate(rotationAnim.value, pivot = center) {
                        for (i in sectorRewards.indices) {
                            val startAngle = i * sectorAngle
                            drawArc(
                                color = sectorColors[i],
                                startAngle = startAngle,
                                sweepAngle = sectorAngle,
                                useCenter = true,
                                topLeft = Offset(center.x - radius, center.y - radius),
                                size = Size(diameter, diameter)
                            )

                            // Sector text
                            val midAngleRad = ((startAngle + sectorAngle / 2f) * PI / 180f).toFloat()
                            val textRadius = radius * 0.65f
                            val textX = center.x + textRadius * cos(midAngleRad)
                            val textY = center.y + textRadius * sin(midAngleRad)

                            drawContext.canvas.nativeCanvas.drawText(
                                "$${sectorRewards[i].toInt()}",
                                textX,
                                textY + 10f,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textSize = 36f
                                    isFakeBoldText = true
                                    textAlign = android.graphics.Paint.Align.CENTER
                                }
                            )
                        }

                        // Gold rim border
                        drawCircle(
                            color = BrightGold,
                            radius = radius,
                            style = Stroke(width = 8f)
                        )
                    }

                    // Top Wheel Center Knob
                    drawCircle(color = BrightGold, radius = 24f)
                    drawCircle(color = DarkNavyBackground, radius = 16f)
                }

                // Pointer at top
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(BrightGold)
                        .border(2.dp, DarkNavyCard, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isSpinning) {
                        isSpinning = true
                        resultText = null
                        val winningIndex = (sectorRewards.indices).random()
                        val targetDegrees = 360f * 5 + (360f - (winningIndex * sectorAngle + sectorAngle / 2f))

                        scope.launch {
                            rotationAnim.animateTo(
                                targetValue = targetDegrees,
                                animationSpec = tween(durationMillis = 3500, easing = FastOutSlowInEasing)
                            )
                            isSpinning = false
                            val wonAmount = sectorRewards[winningIndex]
                            resultText = "🎉 You Won $${wonAmount.toInt()} USD!"
                            onWinReward(wonAmount)
                        }
                    }
                },
                enabled = !isSpinning,
                colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = DarkNavyBackground),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = if (isSpinning) "Spinning Wheel..." else "SPIN NOW",
                    fontWeight = FontWeight.Bold
                )
            }

            if (resultText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = resultText!!,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = BrightGold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
