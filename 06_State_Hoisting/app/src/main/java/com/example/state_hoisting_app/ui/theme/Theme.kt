package com.example.state_hoisting_app.ui.theme

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
import com.example.navigationapp.ui.theme.BackgroundDark
import com.example.navigationapp.ui.theme.BackgroundLight
import com.example.navigationapp.ui.theme.ExpenseRed
import com.example.navigationapp.ui.theme.GreenAccentDark
import com.example.navigationapp.ui.theme.GreenAccentLight
import com.example.navigationapp.ui.theme.GreenContainerDark
import com.example.navigationapp.ui.theme.GreenContainerLight
import com.example.navigationapp.ui.theme.OutlineDark
import com.example.navigationapp.ui.theme.OutlineLight
import com.example.navigationapp.ui.theme.SurfaceDark
import com.example.navigationapp.ui.theme.SurfaceLight
import com.example.navigationapp.ui.theme.TextPrimaryDark
import com.example.navigationapp.ui.theme.TextPrimaryLight
import com.example.navigationapp.ui.theme.TextSecondaryDark
import com.example.navigationapp.ui.theme.TextSecondaryLight

private val LightColorScheme = lightColorScheme(
    primary = GreenAccentLight,
    onPrimary = Color.White,
    primaryContainer = GreenContainerLight,
    onPrimaryContainer = Color(0xFF002114),

    secondary = GreenAccentLight,
    onSecondary = Color.White,

    error = ExpenseRed,
    onError = Color.White,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF0F2F1),
    onSurfaceVariant = TextSecondaryLight,

    outline = OutlineLight
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenAccentDark,
    onPrimary = Color(0xFF003822),
    primaryContainer = GreenContainerDark,
    onPrimaryContainer = Color(0xFF96F7C8),

    secondary = GreenAccentDark,
    onSecondary = Color(0xFF003822),

    error = ExpenseRed,
    onError = Color.White,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = TextSecondaryDark,

    outline = OutlineDark
)

@Composable
fun State_Hoisting_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false by default so your custom expense colors aren't overridden on Android 12+
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