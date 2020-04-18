package ru.helpfulproduction.rates.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils

class AmountEditText: AppCompatEditText {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        imeOptions = IME_OPTIONS
        inputType = INPUT_TYPE
        setOnFocusChangeListener { v, _ ->
            KeyboardUtils.hideKeyboard(v)
        }
    }

    companion object {
        private const val INPUT_TYPE = EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
        private const val IME_OPTIONS = EditorInfo.IME_ACTION_DONE or
                EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
    }

}