package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BrightGold,
    onPrimary = DarkNavyBackground,
    primaryContainer = RoyalBluePrimary,
    onPrimaryContainer = TextPrimaryDark,
    secondary = MetallicGold,
    onSecondary = DarkNavyBackground,
    secondaryContainer = RoyalBlueDark,
    onSecondaryContainer = TextPrimaryDark,
    tertiary = InfoCyan,
    background = DarkNavyBackground,
    onBackground = TextPrimaryDark,
    surface = DarkNavyCard,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkNavySurface,
    onSurfaceVariant = TextSecondaryDark,
    error = ErrorRed,
    outline = GlassBorderGold
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalBluePrimary,
    onPrimary = TextPrimaryDark,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = RoyalBlueDark,
    secondary = GoldDark,
    onSecondary = TextPrimaryDark,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = GoldDark,
    tertiary = InfoCyan,
    background = Color(0xFFF1F5F9),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF475569),
    error = ErrorRed,
    outline = MetallicGold
)

@Composable
fun BaizPayTheme(
    darkTheme: Boolean = true, // Default to luxury dark glassmorphism theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkNavyBackground.toArgb()
            window.navigationBarColor = DarkNavyBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
