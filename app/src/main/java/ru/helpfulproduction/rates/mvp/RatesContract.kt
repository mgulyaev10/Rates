package ru.helpfulproduction.rates.mvp

import io.reactivex.rxjava3.core.Observable
import ru.helpfulproduction.rates.api.rates.GetRates
import ru.helpfulproduction.rates.interfaces.ScrolledToTop
import ru.helpfulproduction.rates.core.CurrenciesAdapter

interface RatesContract {
    interface Presenter: BaseContract.Presenter {
        fun getCurrenciesAdapter(): CurrenciesAdapter
    }

    interface View<P: Presenter>: BaseContract.View<P>, ScrolledToTop

    interface Model<P: Presenter>: BaseContract.Model<P> {
        fun loadRates(mainCurrencyKey: String): Observable<GetRates.GetRatesResponse>
    }
}