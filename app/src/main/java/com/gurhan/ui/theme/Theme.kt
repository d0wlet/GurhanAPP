package com.gurhan.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = TextOnPrimary,
    secondary = AccentGoldLight,
    onSecondary = TextOnPrimary,
    tertiary = ArabicTextDark,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceCard,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkDivider
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = TextOnPrimary,
    secondary = AccentGold,
    onSecondary = TextOnPrimary,
    tertiary = ArabicTextPrimary,
    background = BackgroundCream,
    surface = SurfaceWhite,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor
)

@Composable
fun GurhanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val targetColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    // Animated color scheme
    val colorScheme = ColorScheme(
        primary = animateColorAsState(targetColorScheme.primary, tween(500)).value,
        onPrimary = animateColorAsState(targetColorScheme.onPrimary, tween(500)).value,
        primaryContainer = animateColorAsState(targetColorScheme.primaryContainer, tween(500)).value,
        onPrimaryContainer = animateColorAsState(targetColorScheme.onPrimaryContainer, tween(500)).value,
        inversePrimary = animateColorAsState(targetColorScheme.inversePrimary, tween(500)).value,
        secondary = animateColorAsState(targetColorScheme.secondary, tween(500)).value,
        onSecondary = animateColorAsState(targetColorScheme.onSecondary, tween(500)).value,
        secondaryContainer = animateColorAsState(targetColorScheme.secondaryContainer, tween(500)).value,
        onSecondaryContainer = animateColorAsState(targetColorScheme.onSecondaryContainer, tween(500)).value,
        tertiary = animateColorAsState(targetColorScheme.tertiary, tween(500)).value,
        onTertiary = animateColorAsState(targetColorScheme.onTertiary, tween(500)).value,
        tertiaryContainer = animateColorAsState(targetColorScheme.tertiaryContainer, tween(500)).value,
        onTertiaryContainer = animateColorAsState(targetColorScheme.onTertiaryContainer, tween(500)).value,
        background = animateColorAsState(targetColorScheme.background, tween(500)).value,
        onBackground = animateColorAsState(targetColorScheme.onBackground, tween(500)).value,
        surface = animateColorAsState(targetColorScheme.surface, tween(500)).value,
        onSurface = animateColorAsState(targetColorScheme.onSurface, tween(500)).value,
        surfaceVariant = animateColorAsState(targetColorScheme.surfaceVariant, tween(500)).value,
        onSurfaceVariant = animateColorAsState(targetColorScheme.onSurfaceVariant, tween(500)).value,
        surfaceTint = animateColorAsState(targetColorScheme.surfaceTint, tween(500)).value,
        inverseSurface = animateColorAsState(targetColorScheme.inverseSurface, tween(500)).value,
        inverseOnSurface = animateColorAsState(targetColorScheme.inverseOnSurface, tween(500)).value,
        error = animateColorAsState(targetColorScheme.error, tween(500)).value,
        onError = animateColorAsState(targetColorScheme.onError, tween(500)).value,
        errorContainer = animateColorAsState(targetColorScheme.errorContainer, tween(500)).value,
        onErrorContainer = animateColorAsState(targetColorScheme.onErrorContainer, tween(500)).value,
        outline = animateColorAsState(targetColorScheme.outline, tween(500)).value,
        outlineVariant = animateColorAsState(targetColorScheme.outlineVariant, tween(500)).value,
        scrim = animateColorAsState(targetColorScheme.scrim, tween(500)).value,
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context.findActivity()
            if (activity != null) {
                val window = activity.window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Safe context to activity conversion
private fun android.content.Context.findActivity(): Activity? {
    var context = this
    while (context is android.content.ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
