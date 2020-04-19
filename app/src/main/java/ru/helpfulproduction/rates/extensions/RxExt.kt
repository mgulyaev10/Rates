package ru.helpfulproduction.rates.extensions

import android.content.Context
import io.reactivex.rxjava3.disposables.Disposable
import ru.helpfulproduction.rates.utils.SmartActivity

fun Disposable.disposeOnDestroyOf(activity: SmartActivity): Disposable {
    activity.registerDisposable(this)
    return this
}

fun Disposable.disposeOnDestroyOf(context: Context?): Disposable {
    (context as? SmartActivity)?.let(this::disposeOnDestroyOf)
    return this
}