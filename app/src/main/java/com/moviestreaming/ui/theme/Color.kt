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

val blue_50 = Color(0xFF98A8CB)
val blue_100 = Color(0xff325196)
val blue_200 = Color(0xff101A30)

val accent = Color(0xffcc0033)
val accentDark = Color(0xffC12348)
val red6 = Color(0xffD6243D)
val red_100 = Color(0xffFF4C51)

val gray = Color(0xff252628)
val gray_50 = Color(0xfff6f7f9)
val gray_75 = Color(0xffD0D0D3)
val gray_100 = Color(0xffD9D9D9)
val gray_200 = Color(0xff9FA3AC)
val greyDark = Color(0xff333333)


val grayLight = Color(0xff646567)

val white = Color(0xffFFFFFF) // primary text
val rapunzelSilver = Color(0xffD0D0D4) // secondary text

val black = Color(0xff000000)

val titaniumWhite = Color(0xffe4e4e4) // divider

val blue = Color(0xff3E7BFA) // divider

val green = Color(0xff25AC5B) // like
val green_100 = Color (0xff28C76F)

val orange_100 = Color(0xffFF9F43)

val gradiant1 = listOf(
    Color(0xff4030AA),
    Color(0xff6742CE)
)

val cardBackground2 = Color(0xff2E2E33)

/**
 * Tiger custom Color Palette
 */
@Stable
class TigerColors(
    cardTitleGradiant: List<Color>,
    brand: Color,
    toobarBackground: Color,
    secondary: Color,
    primary: Color,
    brandSecondary: Color,
    uiBackground: Color,
    dialogBackground: Color,
    cardBackground: Color,
    cardSecondaryBackground: Color,
    itemBackground: Color,
    itemBackgroundDark: Color,
    uiBorder: Color,
    textPrimary: Color = brand,
    textSecondary: Color,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    like: Color,
    interactiveAccent: List<Color>,
    policyTextColor: Color,
    buttonTextColor: Color,
    uncheckBoxColor: Color,
    outlinedColor: Color,
    onFocusOutlinedColor: Color,
    circleShapeBackgroundColor: Color,
    dividerColor: Color,
    borderColor: Color,
    progressTrackColor: Color,
    progressRejectedColor: Color,
    progressWaitingColor: Color,
    progressSignedColor: Color,
) {
    var cardTitleGradiant by mutableStateOf(cardTitleGradiant)
        private set
    var brand by mutableStateOf(brand)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var primary by mutableStateOf(primary)
        private set
    var brandSecondary by mutableStateOf(brandSecondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var dialogBackground by mutableStateOf(dialogBackground)
        private set
    var cardBackground by mutableStateOf(cardBackground)
        private set
    var cardSecondaryBackground by mutableStateOf(cardSecondaryBackground)
        private set
    var itemBackground by mutableStateOf(itemBackground)
        private set
    var itemBackgroundDark by mutableStateOf(itemBackgroundDark)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var like by mutableStateOf(like)
        private set
    var interactiveAccent by mutableStateOf(interactiveAccent)
        private set
    var toobarBackground by mutableStateOf(toobarBackground)
        private set
    var policyTextColor by mutableStateOf(policyTextColor)
        private set
    var buttonTextColor by mutableStateOf(buttonTextColor)
        private set
    var uncheckBoxColor by mutableStateOf(uncheckBoxColor)
        private set
    var outlinedColor by mutableStateOf(outlinedColor)
        private set
    var onFocusOutlinedColor by mutableStateOf(onFocusOutlinedColor)
        private set
    var circleShapeBackgroundColor by mutableStateOf(circleShapeBackgroundColor)
        private set
    var dividerColor by mutableStateOf(dividerColor)
        private set
    var borderColor by mutableStateOf(borderColor)
        private set
    var progressTrackColor by mutableStateOf(progressTrackColor)
        private set
    var progressRejectedColor by mutableStateOf(progressRejectedColor)
        private set
    var progressWaitingColor by mutableStateOf(progressWaitingColor)
        private set
    var progressSignedColor by mutableStateOf(progressSignedColor)
        private set

    fun update(other: TigerColors) {
        brand = other.brand
        cardTitleGradiant = other.cardTitleGradiant
        brandSecondary = other.brandSecondary
        uiBackground = other.uiBackground
        dialogBackground = other.dialogBackground
        cardBackground = other.cardBackground
        itemBackground = other.itemBackground
        itemBackgroundDark = other.itemBackgroundDark
        uiBorder = other.uiBorder
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        like = other.like
        interactiveAccent = other.interactiveAccent
        toobarBackground = other.toobarBackground
        policyTextColor = other.policyTextColor
        buttonTextColor = other.buttonTextColor
        uncheckBoxColor = other.uncheckBoxColor
        outlinedColor = other.outlinedColor
        onFocusOutlinedColor = other.onFocusOutlinedColor
        circleShapeBackgroundColor = other.circleShapeBackgroundColor
        dividerColor = other.dividerColor
        borderColor = other.borderColor
        progressTrackColor = other.progressTrackColor
        progressRejectedColor = other.progressRejectedColor
        progressWaitingColor = other.progressWaitingColor
        progressSignedColor = other.progressSignedColor
    }

    fun copy(): TigerColors = TigerColors(
        cardTitleGradiant = cardTitleGradiant,
        brand = brand,
        primary = primary,
        secondary = secondary,
        brandSecondary = brandSecondary,
        uiBackground = uiBackground,
        dialogBackground = dialogBackground,
        cardBackground = cardBackground,
        itemBackground = itemBackground,
        itemBackgroundDark = itemBackgroundDark,
        uiBorder = uiBorder,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        iconPrimary = iconPrimary,
        iconSecondary = iconSecondary,
        like = like,
        interactiveAccent = interactiveAccent,
        cardSecondaryBackground = cardSecondaryBackground,
        toobarBackground = toobarBackground,
        policyTextColor = policyTextColor,
        buttonTextColor = buttonTextColor,
        uncheckBoxColor = uncheckBoxColor,
        outlinedColor = outlinedColor,
        onFocusOutlinedColor = onFocusOutlinedColor,
        circleShapeBackgroundColor = circleShapeBackgroundColor,
        dividerColor = dividerColor,
        borderColor = borderColor,
        progressTrackColor = progressTrackColor,
        progressRejectedColor = progressRejectedColor,
        progressWaitingColor = progressWaitingColor,
        progressSignedColor = progressSignedColor
    )
}