package ru.helpfulproduction.rates.core

import ru.helpfulproduction.rates.utils.Preference
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.extensions.disposeOnDestroyOf
import ru.helpfulproduction.rates.mvp.RatesContract

class RatesPresenter(
    private val view: RatesContract.View<RatesContract.Presenter>
): RatesContract.Presenter, CurrencyChangeListener {

    private val model: RatesContract.Model<RatesContract.Presenter> = RatesModel(this)
    private var mainCurrency: CurrencyItem = Preference.getMainCurrency(view.getContext())
    private val currenciesAdapter = CurrenciesAdapter(mainCurrency, this)

    init {
        model.loadRates(mainCurrency.key)
            .subscribe {
                currenciesAdapter.updateRates(it.rates)
            }
            .disposeOnDestroyOf(view.getContext())
    }

    override fun getCurrenciesAdapter(): CurrenciesAdapter {
        return currenciesAdapter
    }

    override fun onDestroyView() {
        Preference.setMainCurrency(view.getContext(), mainCurrency.key)
    }

    override fun onCurrencyChanged(newCurrency: CurrencyItem) {
        view.scrollToTop()
        mainCurrency = newCurrency
    }

}