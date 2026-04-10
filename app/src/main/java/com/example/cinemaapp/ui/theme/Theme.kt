package com.example.cinemaapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary           = NeonGreen,
    onPrimary         = TextOnAccent,
    secondary         = CardShopping,
    onSecondary       = TextPrimary,
    tertiary          = CardTicket,
    background        = DeepNavy,
    onBackground      = TextPrimary,
    surface           = SurfaceDark,
    onSurface         = TextPrimary,
    surfaceVariant    = SurfaceVariant,
    onSurfaceVariant  = TextSecondary,
    outline           = DividerColor,
//    error             = CardAction,       // reuse for error states
)

@Composable
fun CinemaAppTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DeepNavy.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = AppTypography,
        content     = content
    )
}