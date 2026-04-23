package com.mukesh.rickmortyfan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = ImdbYellow,
        secondary = ImdbLightGrey,
        tertiary = ImdbTextGrey,
        background = ImdbBlack,
        surface = ImdbDarkGrey,
        onPrimary = ImdbBlack,
        onSecondary = ImdbTextWhite,
        onTertiary = ImdbTextWhite,
        onBackground = ImdbTextWhite,
        onSurface = ImdbTextWhite,
        surfaceVariant = ImdbLightGrey,
        onSurfaceVariant = ImdbTextGrey,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = ImdbYellow,
        secondary = ImdbLightGrey,
        tertiary = ImdbTextGrey,
        background = Color.White,
        surface = Color(0xFFF2F2F2),
        onPrimary = ImdbBlack,
        onSecondary = ImdbBlack,
        onTertiary = ImdbBlack,
        onBackground = ImdbBlack,
        onSurface = ImdbBlack,
        surfaceVariant = Color(0xFFE0E0E0),
        onSurfaceVariant = Color.DarkGray,
    )

@Composable
fun RickMortyFanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled to maintain IMDB branding consistency
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
