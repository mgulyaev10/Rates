package ru.helpfulproduction.rates.ui.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
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
            private var isFirstSymbol = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (isZero(s)) {
                    isFirstSymbol = true
                }
            }
            override fun afterTextChanged(s: Editable?) {
                checkCorrectInput(s)
                clearZeroIfFirstSymbol(isFirstSymbol, s)
                recolorText(s)
                notifyAmountChangedIfNeed(s)
                isFirstSymbol = false
            }
        })
    }

    fun setAmountUpdateListener(amountUpdateListener: AmountUpdateListener) {
        this.amountUpdateListener = amountUpdateListener
    }

    fun setTextImpl(text: CharSequence) {
        isUserInput = false
        setText(text)
    }

    private fun checkCorrectInput(s: Editable?) {
        if (isIncorrectInput(s)) {
            s?.insert(0, "0")
        } else if (isIntAndZeroFirst(s) || isFloatAndDoubleZero(s)) {
            s?.delete(0, 1)
        }
    }

    private fun recolorText(s: Editable?) {
        val textColor = if (isZero(s)) {
            R.color.gray_80
        } else {
            R.color.black
        }
        setTextColor(ContextCompat.getColor(context, textColor))
    }

    private fun notifyAmountChangedIfNeed(s: Editable?) {
        if (!isUserInput) {
            isUserInput = true
            return
        }
        amountUpdateListener?.onAmountUpdate(s?.toString())
    }

    private fun isZero(text: CharSequence?): Boolean {
        return text?.toString() == "0"
    }

    private fun isIncorrectInput(text: Editable?): Boolean {
        return (text?.isEmpty() == true || text?.first()?.isDigit() == false)
    }

    private fun isIntAndZeroFirst(text: Editable?): Boolean {
        val length = text?.length ?: return false
        return length > 1 && text.first() == '0' && !text.contains(".")
    }

    private fun isFloatAndDoubleZero(text: Editable?): Boolean {
        val length = text?.length ?: return false
        return length > 1 && text.contains(".") && text.first() == '0' && text[1] == '0'
    }

    private fun clearZeroIfFirstSymbol(isFirstSymbol: Boolean, text: Editable?) {
        if (isFirstSymbol && text?.length == 2 && text[1] != '.') {
            text.delete(1, 2)
        }
    }

    companion object {
        private const val INPUT_TYPE = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL
        private const val IME_OPTIONS = EditorInfo.IME_ACTION_DONE or
                EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
    }

    interface AmountUpdateListener {
        fun onAmountUpdate(amount: String?)
    }

}