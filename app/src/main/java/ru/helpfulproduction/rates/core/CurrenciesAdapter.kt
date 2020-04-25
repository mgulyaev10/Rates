package ru.helpfulproduction.rates.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.currency.CurrencyHolderEventsListener
import ru.helpfulproduction.rates.ui.holders.CurrencyHolder
import ru.helpfulproduction.rates.ui.views.AmountEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils

class CurrenciesAdapter(
    private var currencies: List<CurrencyItem>,
    private val baseCurrencyChangeListener: BaseCurrencyChangeListener
): RecyclerView.Adapter<CurrencyHolder>() {

    init {
        setHasStableIds(true)
    }

    private val currencyHolderListener = object: CurrencyHolderEventsListener {
        override fun onClick(position: Int, view: AmountEditText) {
            KeyboardUtils.showKeyboard(view)
            if (position == 0) {
                return
            }
            baseCurrencyChangeListener.onCurrencyChanged(position)
        }

        override fun onAmountUpdate(position: Int, amount: String) {
            if (position > 0) {
                return
            }
            baseCurrencyChangeListener.onAmountChanged(amount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            CurrencyItem.VIEW_TYPE -> CurrencyHolder(view, currencyHolderListener)
            else -> throw IllegalStateException("Unsupported viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        payloads.forEach { payload ->
            when (payload) {
                CurrencyHolder.PAYLOAD_AMOUNT_TEXT -> holder.bindMoney(currencies[position].amount)
            }
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun getItemId(position: Int): Long {
        return currencies[position].imageRes.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return currencies[position].getViewType()
    }

    fun setItems(items: List<CurrencyItem>) {
        this.currencies = items
    }

}