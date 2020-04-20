package ru.helpfulproduction.rates.mvp

import android.content.Context

interface BaseContract {

    interface Presenter {
        fun onCreateView()
        fun onDestroyView()
    }

    interface View<P: Presenter> {
        var presenter: P?
        fun getContext(): Context?
    }

    interface Model<P: Presenter> {
        var presenter: P?
    }

}