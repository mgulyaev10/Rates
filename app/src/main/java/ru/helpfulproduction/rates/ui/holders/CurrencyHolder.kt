package ru.helpfulproduction.rates.ui.holders

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.core.AmountUpdateListener
import ru.helpfulproduction.rates.recycler.base.BaseViewHolder
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.currency.CurrencyHolderEventsListener
import ru.helpfulproduction.rates.extensions.isZero
import ru.helpfulproduction.rates.extensions.withoutFractionalPart
import ru.helpfulproduction.rates.ui.views.AmountEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils
import java.util.*

class CurrencyHolder(
    view: View,
    listener: CurrencyHolderEventsListener
): BaseViewHolder<CurrencyItem>(view) {

    private val amountUpdateListener = object: AmountUpdateListener {
        override fun onAmountUpdate(amount: String?) {
            val adapterPosition = adapterPosition
            if (amount == null || adapterPosition == RecyclerView.NO_POSITION) {
                return
            }
            listener.onAmountUpdate(adapterPosition, amount)
        }
    }

    private val image = view.findViewById<ImageView>(R.id.image)
    private val title = view.findViewById<TextView>(R.id.title)
    private val subtitle = view.findViewById<TextView>(R.id.subtitle)
    private val money = view.findViewById<AmountEditText>(R.id.money).apply {
        setAmountUpdateListener(amountUpdateListener)
    }

    init {
        view.setOnClickListener {
            val adapterPosition = adapterPosition
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            listener.onClick(adapterPosition, money)
        }
        money.setOnTouchListener { _, motionEvent ->
            val adapterPosition = adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION && motionEvent.action == MotionEvent.ACTION_UP) {
                listener.onClick(adapterPosition, money)
            }
            return@setOnTouchListener false
        }
        money.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                KeyboardUtils.hideKeyboard(v)
            }
        }
    }

    override fun bind(item: CurrencyItem) {
        image.setImageResource(item.imageRes)
        title.text = item.key
        subtitle.text = itemView.context.getString(item.titleRes)
        bindMoney(item.amount)
    }

    fun bindMoney(amount: Float) {
        val text = if (amount.isInfinite() || amount.isNaN()) {
            "0"
        } else if (amount.isZero() || amount.withoutFractionalPart()) {
            amount.toInt().toString()
        } else {
            String.format(Locale.US, "%.2f", amount)
        }
        money.setTextImpl(text)
    }

    companion object {
        const val PAYLOAD_AMOUNT_TEXT = 1424
    }

}