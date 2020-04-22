package ru.helpfulproduction.rates.core.cache

import ru.helpfulproduction.rates.currency.CurrencyItem

object CurrenciesCache: Cache<List<CurrencyItem>> {

    override var cached: List<CurrencyItem>? = null

    override fun save(items: List<CurrencyItem>) {
        cached = items
    }

    override fun get(): List<CurrencyItem>? {
        return cached
    }

}