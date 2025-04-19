package com.moviestreaming.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val primary = Color(0xff292929)
val gray1 = Color(0xff262627)
val primaryDark = Color(0xff1B1B1B)
val secondaryDark = Color(0xff1C1C1C)

val secondary = Color(0xff1C1C1C)

val white = Color(0xffFFFFFF)
val yellow_100 = Color(0xffF8CB06)

val gray_100 = Color(0x7EAFAFAF)
val gray_150 = Color(0x33ffffff)
val gray_200 = Color(0x802C2C2C)
val gray_300 = Color(0x4D2C2C2C)
val gray_400 = Color(0x0D2C2C2C)
val gray_500 = Color(0x08202020)
val gray_600 = Color(0xCC202020)
val gray_700 = Color(0x99202020)

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
    uiBackground: Color,
    startSliderColor: Color,
    centerSliderColor: Color,
    endSliderColor: Color,
    selectIndicatorColor: Color,
    unselectIndicatorColor: Color,
    titleColor: Color,
) {

    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var startSliderColor by mutableStateOf(startSliderColor)
        private set
    var centerSliderColor by mutableStateOf(centerSliderColor)
        private set
    var endSliderColor by mutableStateOf(endSliderColor)
        private set
    var selectIndicatorColor by mutableStateOf(selectIndicatorColor)
        private set
    var unselectIndicatorColor by mutableStateOf(unselectIndicatorColor)
        private set
    var titleColor by mutableStateOf(titleColor)
        private set

    fun update(other: MovieStreamingColors) {
        primary = other.primary
        secondary = other.secondary
        uiBackground = other.uiBackground
        startSliderColor = other.startSliderColor
        centerSliderColor = other.centerSliderColor
        endSliderColor = other.endSliderColor
        selectIndicatorColor = other.selectIndicatorColor
        unselectIndicatorColor = other.unselectIndicatorColor
        titleColor = other.titleColor
    }

    fun copy(): MovieStreamingColors = MovieStreamingColors(
        primary = primary,
        secondary = secondary,
        uiBackground = uiBackground,
        startSliderColor = startSliderColor,
        centerSliderColor = centerSliderColor,
        endSliderColor = endSliderColor,
        selectIndicatorColor = selectIndicatorColor,
        unselectIndicatorColor = unselectIndicatorColor,
        titleColor = titleColor,
    )
}