package ru.helpfulproduction.rates.mvp

import android.content.Context

interface BaseContract {

    interface Presenter<V: View> {
        var view: V?
        fun attachView(v: V)
        fun detachView()
        fun onViewCreated()
    }

    interface View {
        fun getContext(): Context?
    }

    interface Model<P: Presenter<out View>> {
        var presenter: P
    }

}