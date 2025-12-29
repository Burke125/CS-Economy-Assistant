package com.example.cseconomyassistant.ui.theme

import android.app.Activity
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

val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextPrimary,

    secondary = AccentYellow,
    onSecondary = Color.Black,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceVariant,
    outline = BorderSubtle,

    error = ErrorRed
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryVariant,
    onPrimary = Color.White,

    secondary = AccentYellow,
    onSecondary = Color.Black,

    background = Color(0xFFF4F6FA),
    onBackground = Color(0xFF0B0E14),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0B0E14),

    surfaceVariant = Color(0xFFE3E7EF),
    outline = Color(0xFF9AA3B5),

    error = ErrorRed
)

@Composable
fun CSEconomyAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
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