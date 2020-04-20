package ru.helpfulproduction.rates.mvp

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import ru.helpfulproduction.rates.api.rates.GetRates
import ru.helpfulproduction.rates.interfaces.ScrolledToTop
import ru.helpfulproduction.rates.core.CurrenciesAdapter
import ru.helpfulproduction.rates.core.CurrencyChangeListener
import ru.helpfulproduction.rates.currency.CurrencyItem

interface RatesContract {
    interface Presenter: BaseContract.Presenter {
        fun getCurrenciesAdapter(): CurrenciesAdapter
        fun onCurrencyChanged()
        fun onRetryClick()
        fun onStart()
        fun onStop()
    }

    interface View<P: Presenter>: BaseContract.View<P>, ScrolledToTop {
        fun showError()
        fun showLoading()
        fun hideErrorLoading()
    }

    interface Model<P: Presenter>: BaseContract.Model<P>, CurrencyChangeListener {
        fun loadRates(): Observable<GetRates.GetRatesResponse>
        fun getMainCurrency(context: Context?): CurrencyItem
    }
}