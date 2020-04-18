package ru.helpfulproduction.rates.core

import ru.helpfulproduction.rates.mvp.RatesContract

class RatesPresenter(private var view: RatesContract.View<RatesContract.Presenter>?):
    RatesContract.Presenter {

}