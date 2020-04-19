package ru.helpfulproduction.rates.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showKeyboard(view: View) {
        getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun hideKeyboard(view: View) {
        val activity = (view.context as? Activity) ?: return
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity.window.decorView.windowToken,
            0
        )
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        return view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

}