package com.gss.tiger.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val p8 = 8.dp
val p4 = 4.dp
val p12 = 12.dp
val p16 = 16.dp
val p20 = 20.dp

@Stable
class MovieStreamingPadding(
    p4: Dp,
    p8: Dp,
    p12: Dp,
    p16: Dp,
    p20: Dp,
) {
    var p4 by mutableStateOf(p8)
        private set
    var p8 by mutableStateOf(p8)
        private set
    var p12 by mutableStateOf(p12)
        private set
    var p16 by mutableStateOf(p16)
        private set
    var p20 by mutableStateOf(p16)
        private set

    fun update(other: MovieStreamingPadding) {
        p4 = other.p4
        p8 = other.p8
        p12 = other.p12
        p16 = other.p16
        p20 = other.p20
    }

    fun copy(): MovieStreamingPadding = MovieStreamingPadding(
        p8 = p8,
        p12 = p12,
        p16 = p16,
        p4 = p4,
        p20 = p20,
    )
}