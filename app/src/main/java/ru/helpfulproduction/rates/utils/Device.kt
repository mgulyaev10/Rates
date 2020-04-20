package ru.helpfulproduction.rates.utils

import android.os.Build

object Device {

    fun isAtLeastM() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

}