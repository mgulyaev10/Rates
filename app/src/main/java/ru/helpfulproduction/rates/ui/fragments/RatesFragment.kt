package ru.helpfulproduction.rates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.core.RatesPresenter
import ru.helpfulproduction.rates.extensions.setGone
import ru.helpfulproduction.rates.extensions.setVisible
import ru.helpfulproduction.rates.mvp.BaseMvpFragment
import ru.helpfulproduction.rates.utils.KeyboardUtils
import ru.helpfulproduction.rates.utils.ToastUtils

class RatesFragment: BaseMvpFragment<RatesContract.Presenter<RatesContract.View>>(),
    RatesContract.View {

    private lateinit var recycler: RecyclerView
    private lateinit var title: TextView
    private lateinit var appbar: AppBarLayout
    private lateinit var retry: ImageView
    private lateinit var loading: ProgressBar

    override var presenter: RatesContract.Presenter<RatesContract.View> = RatesPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rates, container, false)
        presenter.attachView(this)
        initViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onPause() {
        KeyboardUtils.hideKeyboard(recycler)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun scrollToTop() {
        recycler.scrollToPosition(0)
        appbar.setExpanded(true, true)
    }

    override fun showError() {
        retry.setVisible()
        loading.setGone()
        ToastUtils.showToast(context, R.string.error)
    }

    override fun showLoading() {
        loading.setVisible()
        retry.setGone()
    }

    override fun hideErrorLoading() {
        retry.setGone()
        loading.setGone()
    }

    private fun initViews(view: View) {
        title = view.findViewById(R.id.title)
        appbar = view.findViewById(R.id.appbar)
        loading = view.findViewById(R.id.loading)
        recycler = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = presenter.getCurrenciesAdapter()
        }
        retry = view.findViewById<ImageView>(R.id.retry).apply {
            setOnClickListener {
                presenter.onRetryClick()
            }
        }
    }

    class Builder {
        private val args = Bundle()
        fun build(): RatesFragment {
            return RatesFragment().apply {
                arguments = args
            }
        }
    }

    companion object {
        val TAG = RatesFragment::class.java.simpleName
    }

}