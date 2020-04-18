package ru.helpfulproduction.rates.currency

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.helpfulproduction.rates.recycler.base.BaseItem
import ru.helpfulproduction.rates.R

class CurrencyItem(
    @CurrencyKeys.CurrencyKey val key: String,
    @StringRes val titleRes: Int,
    @DrawableRes val imageRes: Int,
    var amount: Float = 0F,
    var rate: Float? = null
): BaseItem() {

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = R.layout.holder_currency
    }
}