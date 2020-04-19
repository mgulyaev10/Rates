package ru.helpfulproduction.rates.api.rates

import org.json.JSONObject
import ru.helpfulproduction.rates.api.core.ApiResponse
import ru.helpfulproduction.rates.api.core.RepeatableApiRequest
import ru.helpfulproduction.rates.currency.CurrencyKeys
import java.util.concurrent.TimeUnit

class GetRates(
    @CurrencyKeys.CurrencyKey baseCurrencyKey: String
): RepeatableApiRequest<GetRates.GetRatesResponse>(
    PERIOD_REQUEST_MILLIS, "latest") {

    init {
        param(PARAM_BASE_CURRENCY, baseCurrencyKey)
    }

    override fun parse(body: String): GetRatesResponse {
        if (body.isEmpty()) {
            return GetRatesResponse.empty()
        }
        val json = JSONObject(body)
        val baseCurrency = json.getString(KEY_BASE_CURRENCY)
        val ratesJson = json.getJSONObject(KEY_RATES)
        val rates = HashMap<String, Float>()
        ratesJson.keys().forEach { key ->
            val value = ratesJson.getDouble(key).toFloat()
            rates[key] = value
        }
        return GetRatesResponse(
            baseCurrency,
            rates
        )
    }

    class GetRatesResponse(
        val baseCurrency: String,
        val rates: Map<String, Float>
    ): ApiResponse() {
        companion object {
            fun empty(): GetRatesResponse {
                return GetRatesResponse(
                    "",
                    emptyMap()
                )
            }
        }
    }

    companion object {
        private val PERIOD_REQUEST_MILLIS = TimeUnit.SECONDS.toMillis(1)

        private const val PARAM_BASE_CURRENCY = "base"
        private const val KEY_BASE_CURRENCY = "baseCurrency"
        private const val KEY_RATES = "rates"
    }
}