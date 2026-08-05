package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary

@Composable
fun ScratchCardComponent(
    onScratchComplete: (Double) -> Unit
) {
    var isRevealed by remember { mutableStateOf(false) }
    var rewardAmount by remember { mutableStateOf(15.0) }
    val touchedPoints = remember { mutableStateListOf<Offset>() }

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = MetallicGold
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "✨ Golden Scratch Card Reward",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BrightGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Swipe finger over card surface to scratch & reveal",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .size(220.dp, 120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkNavyCard)
                    .border(2.dp, BrightGold, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Underlying Hidden Content
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🏆 YOU WON!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$${rewardAmount.toInt()} USD",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = BrightGold
                    )
                }

                // Scratch Overlay Canvas
                if (!isRevealed) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    touchedPoints.add(change.position)
                                    if (touchedPoints.size > 25 && !isRevealed) {
                                        isRevealed = true
                                        onScratchComplete(rewardAmount)
                                    }
                                }
                            }
                    ) {
                        val path = Path()
                        touchedPoints.forEach { point ->
                            path.addOval(androidx.compose.ui.geometry.Rect(center = point, radius = 35f))
                        }

                        clipPath(path, clipOp = ClipOp.Difference) {
                            drawRect(color = MetallicGold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isRevealed) {
                Text(
                    text = "🎉 Reward Credited to Bonus Balance!",
                    style = MaterialTheme.typography.titleSmall,
                    color = BrightGold,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Button(
                    onClick = {
                        isRevealed = true
                        onScratchComplete(rewardAmount)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary, contentColor = Color.White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Instant Auto Scratch", fontSize = 12.sp)
                }
            }
        }
    }
}
