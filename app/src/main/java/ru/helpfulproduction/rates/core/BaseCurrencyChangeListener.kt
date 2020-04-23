package ru.helpfulproduction.rates.core

interface BaseCurrencyChangeListener {
    fun onCurrencyChanged(newCurrencyPosition: Int)
    fun onAmountChanged(amountString: String)
}