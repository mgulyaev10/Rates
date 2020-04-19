package ru.helpfulproduction.rates.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.currency.CurrencyHelper
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.recycler.core.CurrencyHolderClickListener
import ru.helpfulproduction.rates.extensions.setItemAsFirst
import ru.helpfulproduction.rates.ui.holders.CurrencyHolder
import ru.helpfulproduction.rates.ui.views.AmountEditText
import ru.helpfulproduction.rates.utils.KeyboardUtils

class CurrenciesAdapter(
    private var mainCurrency: CurrencyItem,
    private val mainCurrencyChangeListener: CurrencyChangeListener
): RecyclerView.Adapter<CurrencyHolder>() {

    private var items: List<CurrencyItem> = getSortedItems()

    private val currencyClickListener = object: CurrencyHolderClickListener {
        override fun onClick(position: Int, view: AmountEditText) {
            updateMainCurrency(position)
            view.requestFocusImpl()
            KeyboardUtils.showKeyboard(view)
        }
    }

    fun updateRates(rates: Map<String, Float>) {
        items.forEach { item ->
            val rate = rates[item.key]
            item.rate = rate
        }
        notifyItemRangeChanged(1, items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            CurrencyItem.VIEW_TYPE -> CurrencyHolder(view, currencyClickListener)
            else -> throw IllegalStateException("Unsupported viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    private fun updateMainCurrency(position: Int) {
        if (mainCurrency == items[position]) {
            return
        }
        mainCurrency = items[position]
        moveItemToTop(position)
        mainCurrencyChangeListener.onCurrencyChanged(mainCurrency)
    }

    private fun moveItemToTop(position: Int) {
        // TODO: make notifyItemMoved() and RecyclerView.smoothScrollToTop done together
        items = items.setItemAsFirst(position)
        notifyItemMoved(position, 0)
    }

    private fun getSortedItems() =
        CurrencyHelper.getCurrencies().setItemAsFirst {
            it.key == mainCurrency.key
        }
}