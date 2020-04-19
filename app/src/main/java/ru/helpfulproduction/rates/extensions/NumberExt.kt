package ru.helpfulproduction.rates.extensions

import kotlin.math.sign

fun Float.withoutFractionalPart(): Boolean {
    return ((this * 100).toInt()) % 100 == 0
}

fun Float.isZero(): Boolean {
    return sign(this) == 0F
}