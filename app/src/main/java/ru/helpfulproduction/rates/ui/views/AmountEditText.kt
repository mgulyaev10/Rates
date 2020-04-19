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
import ru.helpfulproduction.rates.AmountUpdateListener
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.utils.SimpleTextWatcher

class AmountEditText: AppCompatEditText {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var amountUpdateListener: AmountUpdateListener? = null
    private var isUserInput = true

    init {
        imeOptions = IME_OPTIONS
        inputType = INPUT_TYPE
        addTextChangedListener(object: SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                checkCorrectInput(s)
                recolorText(s)
                moveCursorToEndIfNeed()
                notifyAmountChangedIfNeed(s)
            }
        })
    }

    fun setAmountUpdateListener(amountUpdateListener: AmountUpdateListener) {
        this.amountUpdateListener = amountUpdateListener
    }

    fun requestFocusImpl() {
        requestFocus()
        moveCursorToEndImpl()
    }

    fun setTextImpl(text: CharSequence) {
        isUserInput = false
        setText(text)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        moveCursorToEndIfNeed()
        return true
    }

    private fun checkCorrectInput(s: Editable?) {
        if (isIncorrectInput(s)) {
            s?.insert(0, "0")
        } else if (isIntAndZeroFirst(s)) {
            s?.delete(0, 1)
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

    private fun notifyAmountChangedIfNeed(s: Editable?) {
        if (!isUserInput) {
            isUserInput = true
            return
        }
        amountUpdateListener?.onAmountUpdate(s?.toString())
    }

    private fun isZero(text: Editable?): Boolean {
        return text?.toString() == "0"
    }

    private fun isIncorrectInput(text: Editable?): Boolean {
        return (text?.isEmpty() == true || text?.first()?.isDigit() == false)
    }

    private fun isIntAndZeroFirst(text: Editable?): Boolean {
        return text?.first() == '0' && text.length > 1 && !text.contains(".")
    }

    companion object {
        private const val INPUT_TYPE = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL
        private const val IME_OPTIONS = EditorInfo.IME_ACTION_DONE or
                EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
    }

}