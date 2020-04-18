package ru.helpfulproduction.rates.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.ui.holders.CurrencyHolder

class CurrenciesAdapter: RecyclerView.Adapter<CurrencyHolder>() {

    private var items: List<CurrencyItem> = CurrencyHelper.getCurrencies()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            CurrencyItem.VIEW_TYPE -> CurrencyHolder(view)
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

}