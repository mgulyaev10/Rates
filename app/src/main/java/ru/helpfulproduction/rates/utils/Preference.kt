package ru.helpfulproduction.rates.utils

import android.content.Context
import ru.helpfulproduction.rates.currency.CurrencyHelper
import ru.helpfulproduction.rates.currency.CurrencyItem

object Preference {
    private const val PREF_NAME = "rates_app_pref"
    private const val KEY_MAIN_CURRENCY = "key_main_currency"

    fun getMainCurrency(context: Context?): CurrencyItem {
        val key = context
            ?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.getString(KEY_MAIN_CURRENCY, null)
        key?.let {
            return CurrencyHelper.from(key)
        }
        return CurrencyHelper.default()
    }

    fun setMainCurrency(context: Context?, currencyKey: String) {
        context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.edit()
            ?.putString(KEY_MAIN_CURRENCY, currencyKey)
            ?.apply()
    }

}