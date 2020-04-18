package ru.helpfulproduction.rates.ui.holders

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.helpfulproduction.rates.recycler.base.BaseViewHolder
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.currency.CurrencyItem
import kotlin.math.sign

class CurrencyHolder(view: View): BaseViewHolder<CurrencyItem>(view) {
    private val image = view.findViewById<ImageView>(R.id.image)
    private val title = view.findViewById<TextView>(R.id.title)
    private val subtitle = view.findViewById<TextView>(R.id.subtitle)
    private val money = view.findViewById<EditText>(R.id.money)

    override fun bind(item: CurrencyItem) {
        image.setImageResource(item.imageRes)
        title.text = item.key
        subtitle.text = itemView.context.getString(item.titleRes)
        bindMoney(item.amount)
    }

    fun bindMoney(amount: Float) {
        val context = itemView.context
        if (sign(amount) == 0F) {
            money.setTextColor(ContextCompat.getColor(context,
                R.color.color_inactive
            ))
            money.setText(amount.toInt().toString())
        } else {
            money.setTextColor(ContextCompat.getColor(context,
                R.color.black
            ))
            money.setText(String.format("%.2f", money))
        }
    }

}