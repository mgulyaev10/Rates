package ru.helpfulproduction.rates.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtils {

    fun showToast(context: Context?, @StringRes textResId: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, textResId, length)
            .show()
    }

}