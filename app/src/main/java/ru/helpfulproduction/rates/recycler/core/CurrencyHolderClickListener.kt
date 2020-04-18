package ru.helpfulproduction.rates.recycler.core

import ru.helpfulproduction.rates.ui.views.AmountEditText

interface CurrencyHolderClickListener {
    fun onClick(position: Int, view: AmountEditText)
}