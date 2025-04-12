package com.moviestreaming.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.gss.tiger.ui.theme.MovieStreamingPadding
import com.gss.tiger.ui.theme.p12
import com.gss.tiger.ui.theme.p16
import com.gss.tiger.ui.theme.p20
import com.gss.tiger.ui.theme.p4
import com.gss.tiger.ui.theme.p8

private val DefaultColorPalette = MovieStreamingColors(
    primary = primary,
    secondary = secondary,
    uiBackground = white
)
private val defaultPadding = MovieStreamingPadding(
    p4 = p4,
    p8 = p8,
    p12 = p12,
    p16 = p16,
    p20 = p20,
)

@Composable
fun TigerTheme(
    content: @Composable () -> Unit
) {
    // returns
    val colors = DefaultColorPalette

        ProvideMovieStreamingColors(colors) {
            ProvideMovieStreamingPadding(defaultPadding) {
                MaterialTheme(
                    typography = Typography,
                    content = {
//                        ProvideTextStyle(TextStyle(color = TigerTheme.colors.textPrimary)) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Surface { content() }
                        }
//                        }
                    },
                    colorScheme = lightColorScheme(
                        primary = MovieStreamingTheme.colors.primary,
                        secondary = MovieStreamingTheme.colors.secondary,
                        background = MovieStreamingTheme.colors.uiBackground,
                    )
                )
            }
        }

}

object MovieStreamingTheme {
    val colors: MovieStreamingColors
        @Composable
        get() = LocalMovieStreamingColors.current
    val padding: MovieStreamingPadding
        @Composable
        get() = LocalMovieStreamingPadding.current
}

@Composable
fun ProvideMovieStreamingColors(
    colors: MovieStreamingColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalMovieStreamingColors provides colorPalette, content = content)
}

@Composable
fun ProvideMovieStreamingPadding(
    paddings: MovieStreamingPadding,
    content: @Composable () -> Unit
) {
    val padding = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        paddings.copy()
    }
    padding.update(paddings)
    CompositionLocalProvider(LocalMovieStreamingPadding provides padding, content = content)
}

private val LocalMovieStreamingColors = staticCompositionLocalOf<MovieStreamingColors> {
    error("No TigerColorPalette provided")
}

private val LocalMovieStreamingPadding = staticCompositionLocalOf<MovieStreamingPadding> {
    error("No TigerPadding provided")
}
