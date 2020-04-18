package ru.helpfulproduction.rates.core

import ru.helpfulproduction.rates.utils.Preference
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.mvp.RatesContract

class RatesPresenter(
    private val view: RatesContract.View<RatesContract.Presenter>
): RatesContract.Presenter,
    CurrencyChangeListener {

    private var mainCurrency: CurrencyItem = Preference.getMainCurrency(view.getContext())

    override fun createAdapter(): CurrenciesAdapter {
        return CurrenciesAdapter(
            mainCurrency,
            this
        )
    }

    override fun onDestroyView() {
        Preference.setMainCurrency(view.getContext(), mainCurrency.key)
    }

    override fun onCurrencyChanged(newCurrency: CurrencyItem) {
        view.scrollToTop()
        mainCurrency = newCurrency
    }

}