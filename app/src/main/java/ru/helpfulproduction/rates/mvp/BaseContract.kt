package ru.helpfulproduction.rates.mvp

import android.content.Context

interface BaseContract {

    interface Presenter

    interface View<P: Presenter> {
        var presenter: P?
        fun getContext(): Context?
    }

}