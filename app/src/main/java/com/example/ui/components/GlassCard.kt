package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderWhite10
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 28.dp,
    borderColor: Color = GlassBorderWhite10,
    contentPadding: Dp = 18.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val glassBrush = Brush.linearGradient(
        colors = listOf(
            RoyalBluePrimary.copy(alpha = 0.45f),
            DarkNavyCard.copy(alpha = 0.85f)
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.6f),
                spotColor = MetallicGold.copy(alpha = 0.12f)
            )
            .clip(shape)
            .background(glassBrush)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .padding(contentPadding),
        content = content
    )
}

