package ru.helpfulproduction.rates.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.utils.SimpleTextWatcher

class AmountEditText: AppCompatEditText {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        imeOptions = IME_OPTIONS
        inputType = INPUT_TYPE
        addTextChangedListener(object: SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                checkCorrectInput(s)
                recolorText(s)
                moveCursorToEndIfNeed()
            }
        })
    }

    fun requestFocusImpl() {
        requestFocus()
        moveCursorToEndImpl()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        moveCursorToEndIfNeed()
        return true
    }

    private fun checkCorrectInput(s: Editable?) {
        if (s?.isEmpty() == true) {
            s.append('0')
        } else if (s?.first() == '0' && s.length > 1) {
            s.delete(0, 1)
        }
    }

    private fun recolorText(s: Editable?) {
        val textColor = if (isZero(s)) {
            R.color.color_inactive
        } else {
            R.color.black
        }
        setTextColor(ContextCompat.getColor(context, textColor))
    }

    private fun moveCursorToEndIfNeed() {
        if (isZero(text)) {
            moveCursorToEndImpl()
        }
    }

    private fun moveCursorToEndImpl() {
        text?.length?.let { length ->
            setSelection(length)
        }
    }

    private fun isZero(text: Editable?): Boolean {
        return text?.toString() == "0"
    }

    companion object {
        private const val INPUT_TYPE = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL
        private const val IME_OPTIONS = EditorInfo.IME_ACTION_DONE or
                EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
    }

}