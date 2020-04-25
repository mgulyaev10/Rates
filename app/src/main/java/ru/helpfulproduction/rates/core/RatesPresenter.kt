package ru.helpfulproduction.rates.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import ru.helpfulproduction.rates.currency.CurrencyItem
import ru.helpfulproduction.rates.mvp.BasePresenter
import ru.helpfulproduction.rates.log.Tracker
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.ui.holders.CurrencyHolder
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

    private val baseCurrencyChangeListener = object : BaseCurrencyChangeListener {
        override fun onAmountChanged(amountString: String) {
            model.updateBaseCurrencyAmount(amountString)
        }

        override fun onCurrencyChanged(newCurrencyPosition: Int) {
            clearDisposable()
            model.updateBaseCurrency(newCurrencyPosition)
            loadRates()
        }
    }

    override fun attachView(v: RatesContract.View) {
        view = v
        if (currenciesAdapter == null) {
            model.initialize(view?.getContext())
            currenciesAdapter = CurrenciesAdapter(model.getCurrencies(), baseCurrencyChangeListener)
        }
    }

    override fun detachView() {
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
        model.saveBaseCurrency(view?.getContext())
        view?.getContext()?.unregisterReceiver(networkStateReceiver)
    }

    override fun onBaseCurrencyChanged(currencies: List<CurrencyItem>, oldBaseCurrencyPosition: Int) {
        currenciesAdapter?.setItems(currencies)
        currenciesAdapter?.notifyItemMoved(oldBaseCurrencyPosition, 0)
        view?.scrollToTop()
    }

    override fun onCurrenciesRecalculated(currencies: List<CurrencyItem>) {
        currenciesAdapter?.setItems(currencies)
        currenciesAdapter?.notifyItemRangeChanged(1, currencies.size, CurrencyHolder.PAYLOAD_AMOUNT_TEXT)
    }

    private fun loadRates() {
        disposable = model.loadRates()
            .doOnSubscribe {
                view?.showLoading()
            }
            .map {
                model.updateRates(it.rates)
                model.getCurrencies()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.hideErrorLoading()
                currenciesAdapter?.setItems(it)
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