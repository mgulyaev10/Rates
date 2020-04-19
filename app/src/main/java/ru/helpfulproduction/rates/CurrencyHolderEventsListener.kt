package ru.helpfulproduction.rates

import ru.helpfulproduction.rates.ui.views.AmountEditText

interface CurrencyHolderEventsListener {
    fun onClick(position: Int, view: AmountEditText)
    fun onAmountUpdate(position: Int, amount: String)
}