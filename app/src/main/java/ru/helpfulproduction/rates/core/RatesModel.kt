package ru.helpfulproduction.rates.core

import io.reactivex.rxjava3.core.Observable
import ru.helpfulproduction.rates.api.rates.GetRates
import ru.helpfulproduction.rates.currency.CurrencyKeys
import ru.helpfulproduction.rates.mvp.BaseModel
import ru.helpfulproduction.rates.mvp.RatesContract

class RatesModel(
    presenter: RatesContract.Presenter
): BaseModel<RatesContract.Presenter>(), RatesContract.Model<RatesContract.Presenter> {

    init {
        this.presenter = presenter
    }

    override fun loadRates(@CurrencyKeys.CurrencyKey mainCurrencyKey: String): Observable<GetRates.GetRatesResponse> {
        return GetRates(mainCurrencyKey).toUiObservable()
    }

}