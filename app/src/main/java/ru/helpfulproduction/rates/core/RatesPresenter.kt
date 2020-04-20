package ru.helpfulproduction.rates.core

import io.reactivex.rxjava3.disposables.Disposable
import ru.helpfulproduction.rates.utils.Preference
import ru.helpfulproduction.rates.extensions.disposeOnDestroyOf
import ru.helpfulproduction.rates.log.Tracker
import ru.helpfulproduction.rates.mvp.RatesContract

class RatesPresenter(
    private val view: RatesContract.View<RatesContract.Presenter>
): RatesContract.Presenter {

    private val model: RatesContract.Model<RatesContract.Presenter> = RatesModel(this)
    private val currenciesAdapter = CurrenciesAdapter(model.getMainCurrency(view.getContext()), model)
    private var disposable: Disposable? = null

    override fun getCurrenciesAdapter(): CurrenciesAdapter {
        return currenciesAdapter
    }

    override fun onRetryClick() {
        loadRates()
    }

    override fun onCreateView() {
        loadRates()
    }

    override fun onDestroyView() {
        Preference.setMainCurrency(view.getContext(), model.getMainCurrency(view.getContext()).key)
    }

    override fun onCurrencyChanged() {
        view.scrollToTop()
    }

    private fun loadRates() {
        disposable = model.loadRates()
            .doOnSubscribe {
                view.showLoading()
            }
            .subscribe({
                view.hideErrorLoading()
                currenciesAdapter.updateRates(it.rates)
            }, {
                view.showError()
                Tracker.logException(it)
                clearDisposable()
            })
            .disposeOnDestroyOf(view.getContext())
    }

    private fun clearDisposable() {
        disposable?.dispose()
        disposable = null
    }

}