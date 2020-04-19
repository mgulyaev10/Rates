package ru.helpfulproduction.rates.core

import ru.helpfulproduction.rates.utils.Preference
import ru.helpfulproduction.rates.extensions.disposeOnDestroyOf
import ru.helpfulproduction.rates.log.Tracker
import ru.helpfulproduction.rates.mvp.RatesContract

class RatesPresenter(
    private val view: RatesContract.View<RatesContract.Presenter>
): RatesContract.Presenter {

    private val model: RatesContract.Model<RatesContract.Presenter> = RatesModel(this)
    private val currenciesAdapter = CurrenciesAdapter(model.getMainCurrency(view.getContext()), model)

    init {
        model.loadRates()
            .subscribe({
                currenciesAdapter.updateRates(it.rates)
            }, {
                Tracker.logException(it)
            })
            .disposeOnDestroyOf(view.getContext())
    }

    override fun getCurrenciesAdapter(): CurrenciesAdapter {
        return currenciesAdapter
    }

    override fun onDestroyView() {
        Preference.setMainCurrency(view.getContext(), model.getMainCurrency(view.getContext()).key)
    }

    override fun onCurrencyChanged() {
        view.scrollToTop()
    }

}