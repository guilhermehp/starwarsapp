package com.example.starwarsapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
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
import com.example.starwarsapp.domain.utils.AppThemes

private val EmpireColorScheme = darkColorScheme(
    primary = DarkCharcoal,
    secondary = GalacticGold,
    tertiary = White,
    background = Background,
    inversePrimary = StarshipBlue
)

private val RebelColorScheme = lightColorScheme(
    primary = White,
    secondary = RebelOrange,
    tertiary = Black,
    background = JediWhite,
    inversePrimary = JediBlue,

)

private val SithColorScheme = darkColorScheme(
    primary = SithBlack,
    secondary = SithRed,
    tertiary = White,
    background = Background,
    inversePrimary = GalacticGold

)

private val JediColorScheme = lightColorScheme(
    primary = LightSaberBlue,
    secondary = JediBlue,
    tertiary = Black,
    background = JediWhite,
    inversePrimary = RebelOrange
)

@Composable
fun StarWarsAppTheme(
    theme: String,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = EmpireColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    when(theme){
        AppThemes.EMPIRE -> {
            MaterialTheme(
                colorScheme = EmpireColorScheme,
                typography = Typography,
                content = content
            )
        }
        AppThemes.REBELS -> {
            MaterialTheme(
                colorScheme = RebelColorScheme,
                typography = Typography,
                content = content
            )
        }
        AppThemes.SITH -> {
            MaterialTheme(
                colorScheme = SithColorScheme,
                typography = Typography,
                content = content
            )
        }
        AppThemes.JEDI -> {
            MaterialTheme(
                colorScheme = JediColorScheme,
                typography = Typography,
                content = content
            )
        }
    }

}