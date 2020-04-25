package ru.helpfulproduction.rates.core.calculator

import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.extensions.divSafe

object RatesCalculator {

    fun calculateAmounts(currentItems: List<CurrencyItem>, baseCurrency: CurrencyItem): List<CurrencyItem> {
        val recalculatedItems = ArrayList<CurrencyItem>()
        val baseCurrencyAmount = baseCurrency.amount
        currentItems.forEach { item ->
            val recalculatedItem = if (item.key == baseCurrency.key) {
                item
            } else {
                item.copy(amount = baseCurrencyAmount * item.rate)
            }
            recalculatedItems.add(recalculatedItem)
        }
        return recalculatedItems
    }

    fun recalculateRates(withItems: List<CurrencyItem>, newBaseCurrency: CurrencyItem) {
        withItems.forEach {
            if (it.key != newBaseCurrency.key) {
                it.rate = it.rate.divSafe(newBaseCurrency.rate)
            }
        }
        newBaseCurrency.rate = 1F
    }

}