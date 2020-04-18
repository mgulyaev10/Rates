package ru.helpfulproduction.rates

import ru.helpfulproduction.rates.mvp.BaseContract

interface RatesContract {
    interface Presenter: BaseContract.Presenter

    interface View<P: Presenter>: BaseContract.View<P>
}