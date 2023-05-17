package com.moviestreaming.utils

fun roundedTo2Decimal(number: Double): Double {
    return String.format("%.1f", number).toDouble()
}