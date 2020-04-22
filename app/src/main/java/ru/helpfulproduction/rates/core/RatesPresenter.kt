package ru.helpfulproduction.rates.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.rxjava3.disposables.Disposable
import ru.helpfulproduction.rates.mvp.BasePresenter
import ru.helpfulproduction.rates.utils.Preference
import ru.helpfulproduction.rates.log.Tracker
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.utils.NetworkState

class RatesPresenter: BasePresenter<RatesContract.View>(), RatesContract.Presenter<RatesContract.View> {

    private val model: RatesContract.Model<RatesContract.Presenter<RatesContract.View>> = RatesModel(this)
    private var currenciesAdapter: CurrenciesAdapter? = null

    private val networkStateReceiver = NetworkStateReceiver()
    private var disposable: Disposable? = null
        set(value) {
            disposable?.dispose()
            field = value
        }

    override fun attachView(v: RatesContract.View) {
        view = v
        if (currenciesAdapter == null) {
            currenciesAdapter = CurrenciesAdapter(model.getMainCurrency(view?.getContext()), model)
        }
    }

    override fun detachView() {
        Preference.setMainCurrency(view?.getContext(), model.getMainCurrency(view?.getContext()).key)
        view = null
    }

    override fun onViewCreated() {
        if (!isRatesUpdating()) {
            loadRates()
        }
    }

    override fun getCurrenciesAdapter(): CurrenciesAdapter {
        return currenciesAdapter ?: throw IllegalStateException("Presenter must be once attached to a view")
    }

    override fun onRetryClick() {
        loadRates()
    }

    override fun onStart() {
        view?.getContext()?.registerReceiver(networkStateReceiver, networkStateReceiver.intentFilter)
    }

    override fun onStop() {
        view?.getContext()?.unregisterReceiver(networkStateReceiver)
    }

    override fun onCurrencyChanged() {
        view?.scrollToTop()
    }

    private fun loadRates() {
        disposable = model.loadRates()
            .doOnSubscribe {
                view?.showLoading()
            }
            .subscribe({
                view?.hideErrorLoading()
                currenciesAdapter?.updateRates(it.rates)
            }, {
                view?.showError()
                Tracker.logException(it)
                clearDisposable()
            })
    }

    private fun clearDisposable() {
        disposable?.dispose()
        disposable = null
    }

    private fun isRatesUpdating(): Boolean {
        return disposable != null && disposable?.isDisposed == false
    }

    private inner class NetworkStateReceiver: BroadcastReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            if (isInitialStickyBroadcast) {
                return
            }
            if (NetworkState.isConnected(context)) {
                loadRates()
            } else {
                view?.showError()
            }
        }
    }
}