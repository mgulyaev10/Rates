package ru.helpfulproduction.rates.core

import ru.helpfulproduction.rates.currency.CurrencyItem

interface CurrencyChangeListener {
    fun onCurrencyChanged(newCurrency: CurrencyItem)
}