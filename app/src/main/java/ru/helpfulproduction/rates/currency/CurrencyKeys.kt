package ru.helpfulproduction.rates.currency

import androidx.annotation.StringDef

object CurrencyKeys {
    const val EUR_KEY = "EUR"
    const val AUD_KEY = "AUD"
    const val BGN_KEY = "BGN"
    const val BRL_KEY = "BRL"
    const val CAD_KEY = "CAD"
    const val CHF_KEY = "CHF"
    const val CNY_KEY = "CNY"
    const val CZK_KEY = "CZK"
    const val DKK_KEY = "DKK"
    const val GBP_KEY = "GBP"
    const val HKD_KEY = "HKD"
    const val HRK_KEY = "HRK"
    const val HUF_KEY = "HUF"
    const val IDR_KEY = "IDR"
    const val ILS_KEY = "ILS"
    const val INR_KEY = "INR"
    const val ISK_KEY = "ISK"
    const val JPY_KEY = "JPY"
    const val KRW_KEY = "KRW"
    const val MXN_KEY = "MXN"
    const val MYR_KEY = "MYR"
    const val NOK_KEY = "NOK"
    const val NZD_KEY = "NZD"
    const val PHP_KEY = "PHP"
    const val PLN_KEY = "PLN"
    const val RON_KEY = "RON"
    const val RUB_KEY = "RUB"
    const val SEK_KEY = "SEK"
    const val SGD_KEY = "SGD"
    const val THB_KEY = "THB"
    const val USD_KEY = "USD"
    const val ZAR_KEY = "ZAR"

    @StringDef(
        EUR_KEY,
        AUD_KEY,
        BGN_KEY,
        BRL_KEY,
        CAD_KEY,
        CHF_KEY,
        CNY_KEY,
        CZK_KEY,
        DKK_KEY,
        GBP_KEY,
        HKD_KEY,
        HRK_KEY,
        HUF_KEY,
        IDR_KEY,
        ILS_KEY,
        INR_KEY,
        ISK_KEY,
        JPY_KEY,
        KRW_KEY,
        MXN_KEY,
        MYR_KEY,
        NOK_KEY,
        NZD_KEY,
        PHP_KEY,
        PLN_KEY,
        RON_KEY,
        RUB_KEY,
        SEK_KEY,
        SGD_KEY,
        THB_KEY,
        USD_KEY,
        ZAR_KEY
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class CurrencyKey
}