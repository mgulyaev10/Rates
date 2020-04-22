package ru.helpfulproduction.rates.mvp

abstract class BasePresenter<V: BaseContract.View>: BaseContract.Presenter<V> {

    override var view: V? = null
    
}