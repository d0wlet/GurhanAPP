package com.gurhan.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = TealSurface,
    primaryContainer = TealPrimaryLight,
    secondary = TealPrimaryLight,
    background = TealBackground,
    surface = TealSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = TealBorder
)

@Composable
fun GurhanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
