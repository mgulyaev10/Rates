package ru.helpfulproduction.rates.log

import android.util.Log

object Tracker {
    fun logException(e: Throwable) {
        Log.e(Thread.currentThread().name, e.message, e)
    }
}