package ru.helpfulproduction.rates.core

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import ru.helpfulproduction.rates.MainCurrencyKeyProvider
import ru.helpfulproduction.rates.api.rates.GetRates
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.mvp.BaseModel
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.utils.Preference

class RatesModel(
    presenter: RatesContract.Presenter
): BaseModel<RatesContract.Presenter>(), RatesContract.Model<RatesContract.Presenter> {

    private var mainCurrency: CurrencyItem? = null
    private val mainCurrencyKeyProvider = object: MainCurrencyKeyProvider {
        override fun getMainCurrencyKey(): String {
            return mainCurrency?.key ?: throw IllegalStateException("mainCurrency has not initialized yet!")
        }
    }

    init {
        this.presenter = presenter
    }

    override fun loadRates(): Observable<GetRates.GetRatesResponse> {
        return GetRates(mainCurrencyKeyProvider).toUiObservable()
    }

    override fun getMainCurrency(context: Context?): CurrencyItem {
        return mainCurrency ?: Preference.getMainCurrency(context).also {
            this.mainCurrency = it
        }
    }

    override fun onCurrencyChanged(newCurrency: CurrencyItem) {
        presenter?.onCurrencyChanged()
        mainCurrency = newCurrency
    }

}