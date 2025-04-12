package com.moviestreaming.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val primary = Color(0xffAAD7FF)
val gray1 = Color(0xff262627)
val primaryDark = Color(0xff0C0C0C)
val secondaryDark = Color(0xff1C1C1C)

val secondary = Color(0xff1C1C1C)

val white = Color(0xffFFFFFF)

val gradiant1 = listOf(
    Color(0xff4030AA),
    Color(0xff6742CE)
)

/**
 * Movie Streaming custom Color Palette
 */
@Stable
class MovieStreamingColors(
    primary: Color,
    secondary: Color,
    uiBackground: Color
) {

    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set

    fun update(other: MovieStreamingColors) {
        primary = other.primary
        secondary = other.secondary
        uiBackground = other.uiBackground
    }

    fun copy(): MovieStreamingColors = MovieStreamingColors(
        primary = primary,
        secondary = secondary,
        uiBackground = uiBackground
    )
}