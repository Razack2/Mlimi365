package com.example.mlimi365.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4ADE80),
    secondary = Color(0xFF86EFAC),
    tertiary = Color(0xFFA7F3D0),
    background = Color(0xFF111827),
    surface = Color(0xFF1F2937),
    onPrimary = Color(0xFF052E16),
    onSecondary = Color(0xFF052E16),
    onBackground = Color(0xFFF9FAFB),
    onSurface = Color(0xFFF9FAFB),
)

private val LightColorScheme = lightColorScheme(
    primary = MlimiGreenPrimary,
    secondary = MlimiGreenSecondary,
    tertiary = Color(0xFF15803D),
    background = MlimiBackground,
    surface = MlimiSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = MlimiDarkText,
    onSurface = MlimiDarkText,
    primaryContainer = MlimiGreenLight,
    onPrimaryContainer = MlimiGreenPrimary,
)

@Composable
fun Mlimi365Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Keep consistent brand colors across devices
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
        content = content,
    )
}
