package ru.helpfulproduction.rates.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.currency.CurrencyHelper
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.extensions.setItemAsFirst
import ru.helpfulproduction.rates.currency.CurrencyHolderEventsListener
import ru.helpfulproduction.rates.extensions.isZero
import ru.helpfulproduction.rates.ui.holders.CurrencyHolder
import ru.helpfulproduction.rates.ui.views.AmountEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils

class CurrenciesAdapter(
    private var mainCurrency: CurrencyItem,
    private val mainCurrencyChangeListener: CurrencyChangeListener
): RecyclerView.Adapter<CurrencyHolder>() {

    init {
        setHasStableIds(true)
    }

    private var items: List<CurrencyItem> = getSortedItems()
        set(value) {
            field = value
            mainCurrency.rate = 1F
        }

    private val currencyHolderListener = object:
        CurrencyHolderEventsListener {
        override fun onClick(position: Int, view: AmountEditText) {
            updateMainCurrency(position)
            view.requestFocusImpl()
            KeyboardUtils.showKeyboard(view)
        }

        override fun onAmountUpdate(position: Int, amount: String) {
            if (position > 0 || amount.isEmpty() || amount.toFloat().isZero() && mainCurrency.amount.isZero()) {
                return
            }
            val mainCurrencyAmount = amount.toFloat()
            mainCurrency.amount = mainCurrencyAmount
            recalculateItems()
        }
    }

    fun updateRates(rates: Map<String, Float>) {
        items.forEach { item ->
            val rate = rates[item.key] ?: 1F
            item.rate = rate
        }
        if (mainCurrency.amount.isZero()) {
            return
        }
        recalculateItems()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            CurrencyItem.VIEW_TYPE -> CurrencyHolder(view, currencyHolderListener)
            else -> throw IllegalStateException("Unsupported viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        payloads.forEach { payload ->
            when (payload) {
                CurrencyHolder.PAYLOAD_AMOUNT_TEXT -> holder.bindMoney(items[position].amount)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].imageRes.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    private fun updateMainCurrency(position: Int) {
        if (mainCurrency == items[position]) {
            return
        }
        mainCurrency.rate = 1F / items[position].rate
        mainCurrency = items[position]
        moveItemToTop(position)
        mainCurrencyChangeListener.onCurrencyChanged(mainCurrency)
    }

    private fun moveItemToTop(position: Int) {
        // TODO: make notifyItemMoved() and RecyclerView.smoothScrollToTop done together
        items = items.setItemAsFirst(position)
        notifyItemMoved(position, 0)
    }

    private fun recalculateItems() {
        val mainCurrencyAmount = mainCurrency.amount
        items.forEach {
            it.amount = mainCurrencyAmount * it.rate
        }
        notifyItemRangeChanged(1, items.size, CurrencyHolder.PAYLOAD_AMOUNT_TEXT)
    }

    private fun getSortedItems() =
        CurrencyHelper.getCurrencies().setItemAsFirst {
            it.key == mainCurrency.key
        }
}