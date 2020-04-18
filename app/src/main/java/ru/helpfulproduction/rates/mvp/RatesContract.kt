package ru.helpfulproduction.rates.mvp

interface RatesContract {
    interface Presenter: BaseContract.Presenter

    interface View<P: Presenter>: BaseContract.View<P>
}