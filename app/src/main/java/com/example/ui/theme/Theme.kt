package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = CrimsonPrimary,
    onPrimary = Color.White,
    primaryContainer = CrimsonLight.copy(alpha = 0.15f),
    onPrimaryContainer = CrimsonDark,
    secondary = GoldPrimary,
    onSecondary = Color.White,
    secondaryContainer = GoldShimmer.copy(alpha = 0.35f),
    onSecondaryContainer = Color(0xFF78350F),
    tertiary = CrimsonMedium,
    background = CreamBackground,
    onBackground = SlateTextDark,
    surface = Color.White,
    onSurface = SlateTextDark,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = SlateTextMedium,
    outline = CrimsonBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = CrimsonLight,
    onPrimary = Color.White,
    primaryContainer = CrimsonDark,
    onPrimaryContainer = GoldShimmer,
    secondary = GoldSecondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = GoldShimmer,
    background = Color(0xFF1A1213),
    onBackground = Color(0xFFF1E9E9),
    surface = Color(0xFF24191A),
    onSurface = Color(0xFFF1E9E9),
    surfaceVariant = Color(0xFF332325),
    onSurfaceVariant = Color(0xFFDAC1C3),
    outline = Color(0xFF5A3E41)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We prioritize our intentional Crimson-Gold academic branding for brand consistency
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
