package ru.helpfulproduction.rates.currency

import ru.helpfulproduction.rates.R

object CurrencyHelper {

    private val currencies = mapOf(
        CurrencyKeys.EUR_KEY to CurrencyItem(CurrencyKeys.EUR_KEY, R.string.euro, R.drawable.ic_euro_40),
        CurrencyKeys.AUD_KEY to CurrencyItem(CurrencyKeys.AUD_KEY, R.string.australian_dollar, R.drawable.ic_australia_40),
        CurrencyKeys.BGN_KEY to CurrencyItem(CurrencyKeys.BGN_KEY, R.string.bulgarian_lev, R.drawable.ic_bulgaria_40),
        CurrencyKeys.BRL_KEY to CurrencyItem(CurrencyKeys.BRL_KEY, R.string.brazilian_real, R.drawable.ic_brazil_40),
        CurrencyKeys.CAD_KEY to CurrencyItem(CurrencyKeys.CAD_KEY, R.string.canadian_dollar, R.drawable.ic_canada_40),
        CurrencyKeys.CHF_KEY to CurrencyItem(CurrencyKeys.CHF_KEY, R.string.swiss_franc, R.drawable.ic_switzerland_40),
        CurrencyKeys.CNY_KEY to CurrencyItem(CurrencyKeys.CNY_KEY, R.string.chinese_yuan, R.drawable.ic_china_40),
        CurrencyKeys.CZK_KEY to CurrencyItem(CurrencyKeys.CZK_KEY, R.string.czech_koruna, R.drawable.ic_czech_40),
        CurrencyKeys.DKK_KEY to CurrencyItem(CurrencyKeys.DKK_KEY, R.string.danish_krone, R.drawable.ic_denmark_40),
        CurrencyKeys.GBP_KEY to CurrencyItem(CurrencyKeys.GBP_KEY, R.string.pound_sterling, R.drawable.ic_united_kingdom_40),
        CurrencyKeys.HKD_KEY to CurrencyItem(CurrencyKeys.HKD_KEY, R.string.hong_kong_dollar, R.drawable.ic_hong_kong_40),
        CurrencyKeys.HRK_KEY to CurrencyItem(CurrencyKeys.HRK_KEY, R.string.croatian_kuna, R.drawable.ic_croatia_40),
        CurrencyKeys.HUF_KEY to CurrencyItem(CurrencyKeys.HUF_KEY, R.string.hungarian_forint, R.drawable.ic_hungary_40),
        CurrencyKeys.IDR_KEY to CurrencyItem(CurrencyKeys.IDR_KEY, R.string.indonesian_rupiah, R.drawable.ic_indonesia_40),
        CurrencyKeys.ILS_KEY to CurrencyItem(CurrencyKeys.ILS_KEY, R.string.israeli_new_shekel, R.drawable.ic_israel_40),
        CurrencyKeys.INR_KEY to CurrencyItem(CurrencyKeys.INR_KEY, R.string.indian_rupee, R.drawable.ic_india_40),
        CurrencyKeys.ISK_KEY to CurrencyItem(CurrencyKeys.ISK_KEY, R.string.icelandic_króna, R.drawable.ic_iceland_40),
        CurrencyKeys.JPY_KEY to CurrencyItem(CurrencyKeys.JPY_KEY, R.string.japanese_yen, R.drawable.ic_japan_40),
        CurrencyKeys.KRW_KEY to CurrencyItem(CurrencyKeys.KRW_KEY, R.string.south_korean, R.drawable.ic_south_korea_40),
        CurrencyKeys.MXN_KEY to CurrencyItem(CurrencyKeys.MXN_KEY, R.string.mexican_peso, R.drawable.ic_mexico_40),
        CurrencyKeys.MYR_KEY to CurrencyItem(CurrencyKeys.MYR_KEY, R.string.malaysian_ringgit, R.drawable.ic_malaysia_40),
        CurrencyKeys.NOK_KEY to CurrencyItem(CurrencyKeys.NOK_KEY, R.string.norwegian_krone, R.drawable.ic_norway_40),
        CurrencyKeys.NZD_KEY to CurrencyItem(CurrencyKeys.NZD_KEY, R.string.new_zealand_dollar, R.drawable.ic_new_zealand_40),
        CurrencyKeys.PHP_KEY to CurrencyItem(CurrencyKeys.PHP_KEY, R.string.philippine_peso, R.drawable.ic_philippines_40),
        CurrencyKeys.PLN_KEY to CurrencyItem(CurrencyKeys.PLN_KEY, R.string.poland_zloty, R.drawable.ic_poland_40),
        CurrencyKeys.RON_KEY to CurrencyItem(CurrencyKeys.RON_KEY, R.string.romanian_leu, R.drawable.ic_romania_40),
        CurrencyKeys.RUB_KEY to CurrencyItem(CurrencyKeys.RUB_KEY, R.string.russian_ruble, R.drawable.ic_russia_40),
        CurrencyKeys.SEK_KEY to CurrencyItem(CurrencyKeys.SEK_KEY, R.string.swedish_krona, R.drawable.ic_sweden_40),
        CurrencyKeys.SGD_KEY to CurrencyItem(CurrencyKeys.SGD_KEY, R.string.singapore_dollar, R.drawable.ic_singapore_40),
        CurrencyKeys.THB_KEY to CurrencyItem(CurrencyKeys.THB_KEY, R.string.thai_baht, R.drawable.ic_thailand_40),
        CurrencyKeys.USD_KEY to CurrencyItem(CurrencyKeys.USD_KEY, R.string.us_dollar, R.drawable.ic_united_states_40),
        CurrencyKeys.ZAR_KEY to CurrencyItem(CurrencyKeys.ZAR_KEY, R.string.south_african_rand, R.drawable.ic_south_africa_40)
    )


    fun getCurrencies(): List<CurrencyItem> {
        return currencies.values.toList()
    }

    fun from(@CurrencyKeys.CurrencyKey key: String): CurrencyItem {
        return currencies[key] ?: error("Invalid key: $key")
    }

    fun default(): CurrencyItem = currencies[CurrencyKeys.EUR_KEY] ?: error("There is no values")

}