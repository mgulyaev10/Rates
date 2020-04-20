package ru.helpfulproduction.rates.log

import android.util.Log
import com.crashlytics.android.Crashlytics

object Tracker {

    fun logException(e: Throwable) {
        Log.e(Thread.currentThread().name, e.message, e)
        Crashlytics.logException(e)
    }

}