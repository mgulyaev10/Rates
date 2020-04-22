package ru.helpfulproduction.rates.mvp

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseMvpFragment<P: BaseContract.Presenter<out BaseContract.View>>: Fragment(),
    BaseContract.View {

    abstract var presenter: P

    override fun getContext(): Context? = super.getContext()

}