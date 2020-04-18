package ru.helpfulproduction.rates.ui.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.helpfulproduction.rates.recycler.base.BaseViewHolder
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.recycler.core.CurrencyHolderClickListener
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.ui.views.AmountEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils
import kotlin.math.sign

class CurrencyHolder(
    view: View,
    currencyClickListener: CurrencyHolderClickListener
): BaseViewHolder<CurrencyItem>(view) {

    private val image = view.findViewById<ImageView>(R.id.image)
    private val title = view.findViewById<TextView>(R.id.title)
    private val subtitle = view.findViewById<TextView>(R.id.subtitle)
    private val money = view.findViewById<AmountEditText>(R.id.money).apply {

    }

    init {
        view.setOnClickListener {
            currencyClickListener.onClick(adapterPosition, money)
        }
        money.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                currencyClickListener.onClick(adapterPosition, money)
            } else {
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
        val text = if (sign(amount) == 0F) {
            amount.toInt().toString()
        } else {
            String.format("%.2f", amount)
        }
        money.setText(text)
    }

}