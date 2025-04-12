package com.gss.tiger.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.moviestreaming.ui.theme.TigerColors
import com.moviestreaming.ui.theme.accent
import com.moviestreaming.ui.theme.accentDark
import com.moviestreaming.ui.theme.blue
import com.moviestreaming.ui.theme.blue_100
import com.moviestreaming.ui.theme.blue_200
import com.moviestreaming.ui.theme.blue_50
import com.moviestreaming.ui.theme.cardBackground2
import com.moviestreaming.ui.theme.gradiant1
import com.moviestreaming.ui.theme.gray1
import com.moviestreaming.ui.theme.gray_100
import com.moviestreaming.ui.theme.gray_200
import com.moviestreaming.ui.theme.gray_50
import com.moviestreaming.ui.theme.gray_75
import com.moviestreaming.ui.theme.green
import com.moviestreaming.ui.theme.green_100
import com.moviestreaming.ui.theme.orange_100
import com.moviestreaming.ui.theme.primary
import com.moviestreaming.ui.theme.primaryDark
import com.moviestreaming.ui.theme.rapunzelSilver
import com.moviestreaming.ui.theme.red_100
import com.moviestreaming.ui.theme.secondaryDark
import com.moviestreaming.ui.theme.titaniumWhite
import com.moviestreaming.ui.theme.white

private val DefaultColorPalette = TigerColors(
    brand = accent,
    cardTitleGradiant = gradiant1,
    brandSecondary = blue,
    uiBackground = white,
    dialogBackground = gray1,
    cardBackground = gray1,
    itemBackground = primary,
    itemBackgroundDark = secondaryDark,
    uiBorder = titaniumWhite,
    textPrimary = white,
    iconSecondary = rapunzelSilver,
    textSecondary = rapunzelSilver,
    iconPrimary = white,
    like = green,
    primary = primary,
    secondary = secondaryDark,
    interactiveAccent = listOf(accent, accentDark),
    cardSecondaryBackground = cardBackground2,
    toobarBackground = primaryDark,
    policyTextColor = blue_100,
    buttonTextColor = blue_200,
    uncheckBoxColor = blue_100,
    outlinedColor = gray_200,
    onFocusOutlinedColor = blue_100,
    circleShapeBackgroundColor = gray_50,
    dividerColor = gray_75,
    borderColor = blue_50,
    progressTrackColor = gray_100,
    progressRejectedColor = red_100,
    progressWaitingColor = orange_100,
    progressSignedColor = green_100
)
private val defaultPadding = TigerPadding(
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

        ProvideTigerColors(colors) {
            ProvideTigerPadding(defaultPadding) {
                MaterialTheme(
                    typography = TigerTypography,
                    content = {
//                        ProvideTextStyle(TextStyle(color = TigerTheme.colors.textPrimary)) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Surface { content() }
                        }
//                        }
                    },
                    colorScheme = lightColorScheme(
                        primary = TigerTheme.colors.primary,
                        secondary = TigerTheme.colors.secondary,
                        background = TigerTheme.colors.uiBackground,
                    )
                )
            }
        }

}

object TigerTheme {
    val colors: TigerColors
        @Composable
        get() = LocalTigerColors.current
    val padding: TigerPadding
        @Composable
        get() = LocalTigerPadding.current
}

@Composable
fun ProvideTigerColors(
    colors: TigerColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalTigerColors provides colorPalette, content = content)
}

@Composable
fun ProvideTigerPadding(
    paddings: TigerPadding,
    content: @Composable () -> Unit
) {
    val padding = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        paddings.copy()
    }
    padding.update(paddings)
    CompositionLocalProvider(LocalTigerPadding provides padding, content = content)
}

private val LocalTigerColors = staticCompositionLocalOf<TigerColors> {
    error("No TigerColorPalette provided")
}

private val LocalTigerPadding = staticCompositionLocalOf<TigerPadding> {
    error("No TigerPadding provided")
}
