package ru.helpfulproduction.rates.mvp

abstract class BaseModel<P: BaseContract.Presenter>: BaseContract.Model<P> {
    override var presenter: P? = null
}