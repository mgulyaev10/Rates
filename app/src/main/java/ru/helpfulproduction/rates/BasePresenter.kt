package ru.helpfulproduction.rates

import ru.helpfulproduction.rates.mvp.BaseContract

abstract class BasePresenter<V: BaseContract.View>: BaseContract.Presenter<V> {

    override var view: V? = null
    
}