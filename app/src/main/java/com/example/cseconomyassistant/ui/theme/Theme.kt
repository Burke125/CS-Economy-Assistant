package com.example.cseconomyassistant.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextPrimary,

    secondary = AccentYellowDark,
    onSecondary = Color.Black,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceVariant,
    outline = BorderSubtle,
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
)

@Composable
fun CSEconomyAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
            content = content
        )
    }
}