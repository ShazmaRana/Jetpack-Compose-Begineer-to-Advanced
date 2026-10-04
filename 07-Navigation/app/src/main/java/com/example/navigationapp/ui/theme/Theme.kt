package com.example.navigationapp.ui.theme


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
    primary = deepbluedark,
    onPrimary = Color.White,
    primaryContainer = deepbluelight,
    onPrimaryContainer = deepblueDeep,
    secondary = electricpurpledark,
    onSecondary = Color.White,
    secondaryContainer = electricpurplelight,
    tertiary = vibrantgreendark,
    onTertiary = Color.White,
    tertiaryContainer = vibrantgreenlight,
    background = surfaceLight,
    onBackground = onSurfaceLight,
    surface = Color.White,
    onSurface = onSurfaceLight
)

private val DarkColorScheme = darkColorScheme(
    primary = deepblueSoft,
    onPrimary = deepblueDeep,
    primaryContainer = deepblueDeep,
    onPrimaryContainer = deepbluelight,
    secondary = electricpurpledark,
    onSecondary = Color.White,
    tertiary = vibrantgreendark,
    onTertiary = Color.White,
    background = surfaceDark,
    onBackground = onSurfaceDark,
    surface = Color(0xFF181E27),
    onSurface = onSurfaceDark
)

@Composable
fun NavigationAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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