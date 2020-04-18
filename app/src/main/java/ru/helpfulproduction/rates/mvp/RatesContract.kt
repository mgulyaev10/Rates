package ru.helpfulproduction.rates.mvp

import ru.helpfulproduction.rates.interfaces.ScrolledToTop
import ru.helpfulproduction.rates.core.CurrenciesAdapter

interface RatesContract {
    interface Presenter: BaseContract.Presenter {
        fun createAdapter(): CurrenciesAdapter
    }

    interface View<P: Presenter>: BaseContract.View<P>,
        ScrolledToTop
}