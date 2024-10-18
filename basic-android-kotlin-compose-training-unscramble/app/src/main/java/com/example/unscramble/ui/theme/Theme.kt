package com.example.unscramble.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define tus colores personalizados
val LightGrayColor = Color(0xFFD3D3D3) // Gris claro
val BlueColor = Color(0xFF0000FF) // Azul

private val LightColors = lightColorScheme(
    primary = BlueColor, // Cambia aquí si lo deseas
    onPrimary = Color.White,
    primaryContainer = LightGrayColor, // Cambia aquí si lo deseas
    onPrimaryContainer = Color.Black,
    secondary = Color.Green, // Personaliza otros colores si lo deseas
    onSecondary = Color.White,
    background = LightGrayColor,
    onBackground = Color.Black,
    surface = LightGrayColor,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = BlueColor, // Cambia aquí si lo deseas
    onPrimary = Color.White,
    primaryContainer = Color.DarkGray, // Cambia aquí si lo deseas
    onPrimaryContainer = Color.White,
    secondary = Color.Cyan, // Personaliza otros colores si lo deseas
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Gray,
    onSurface = Color.Black,
)

@Composable
fun UnscrambleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}
