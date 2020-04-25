package ru.helpfulproduction.rates.core

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import ru.helpfulproduction.rates.api.rates.GetRates
import ru.helpfulproduction.rates.core.cache.CurrenciesCache
import ru.helpfulproduction.rates.currency.CurrencyHelper
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.extensions.divSafe
import ru.helpfulproduction.rates.extensions.isZero
import ru.helpfulproduction.rates.extensions.setItemAsFirst
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.utils.Preference

class RatesModel<P: RatesContract.Presenter<RatesContract.View>> (
    override var presenter: P
): RatesContract.Model<P> {

    private lateinit var items: List<CurrencyItem>
    private lateinit var baseCurrency: CurrencyItem

    private val baseCurrencyKeyProvider = object: BaseCurrencyKeyProvider {
        override fun getBaseCurrencyKey(): String {
            return baseCurrency.key
        }
    }

    override fun initialize(context: Context?) {
        baseCurrency = Preference.getBaseCurrency(context)
        items = getSortedItems()
    }

    override fun loadRates(): Observable<GetRates.GetRatesResponse> {
        return GetRates(baseCurrencyKeyProvider).toUiObservable()
    }

    override fun updateBaseCurrencyAmount(amountString: String) {
        if (amountString.isEmpty() || amountString.toFloat().isZero() && baseCurrency.amount.isZero()) {
            return
        }
        val baseCurrencyAmount = amountString.toFloat()
        baseCurrency.amount = baseCurrencyAmount
        recalculateItems()
    }

    override fun updateBaseCurrency(newBaseCurrencyPosition: Int) {
        val newCurrency = items[newBaseCurrencyPosition]
        updatePreviousBaseCurrency(newCurrency)
        val newItems = items.setItemAsFirst(newBaseCurrencyPosition)
        setItemsImpl(newItems)
        presenter.onBaseCurrencyChanged(newItems, newBaseCurrencyPosition)
        baseCurrency = newCurrency
    }

    override fun updateRates(newRates: Map<String, Float>) {
        items.forEach { item ->
            val rate = newRates[item.key] ?: 1F
            item.rate = rate
        }
        if (baseCurrency.amount.isZero()) {
            return
        }
        recalculateItems()
    }

    override fun saveBaseCurrency(context: Context?) {
        Preference.setBaseCurrency(context, baseCurrency.key)
    }

    override fun getCurrencies(): List<CurrencyItem> {
        return items
    }

    override fun getBaseCurrency(context: Context?): CurrencyItem {
        return baseCurrency
    }

    private fun updatePreviousBaseCurrency(newBaseCurrency: CurrencyItem) {
        items.forEach {
            if (it.key != newBaseCurrency.key) {
                it.rate = it.rate.divSafe(newBaseCurrency.rate)
            }
        }
        newBaseCurrency.rate = 1F
        baseCurrency.amount = baseCurrency.rate * newBaseCurrency.amount
    }

    private fun recalculateItems() {
        val baseCurrencyAmount = baseCurrency.amount
        items.forEach {
            it.amount = baseCurrencyAmount * it.rate
        }
        presenter.onCurrenciesRecalculated(items)
    }

    private fun setItemsImpl(items: List<CurrencyItem>) {
        this.items = items
        CurrenciesCache.save(items)
    }

    private fun getSortedItems(): List<CurrencyItem> {
        val cached = CurrenciesCache.get()
        return cached ?: CurrencyHelper.getCurrencies().setItemAsFirst {
            it.key == baseCurrency.key
        }
    }

}