package ru.helpfulproduction.rates.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils
import ru.helpfulproduction.rates.utils.SimpleTextWatcher

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
        addTextChangedListener(object: SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                checkCorrectInput(s)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        if (text?.length == 1) {
            setSelection(1)
        }
        return true
    }

    private fun checkCorrectInput(s: Editable?) {
        if (s?.isEmpty() == true) {
            s.append('0')
        } else if (s?.first() == '0' && s.length > 1) {
            s.delete(0, 1)
        }
    }

    companion object {
        private const val INPUT_TYPE = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL
        private const val IME_OPTIONS = EditorInfo.IME_ACTION_DONE or
                EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
    }

}