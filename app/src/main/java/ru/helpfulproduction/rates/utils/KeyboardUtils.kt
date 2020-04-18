package ru.helpfulproduction.rates.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showKeyboard(view: View) {
        getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        return view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

}