package ru.helpfulproduction.rates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.mvp.RatesContract
import ru.helpfulproduction.rates.core.RatesPresenter
import ru.helpfulproduction.rates.mvp.BaseMvpFragment

class RatesFragment: BaseMvpFragment<RatesContract.Presenter>(),
    RatesContract.View<RatesContract.Presenter> {

    private lateinit var recycler: RecyclerView
    private lateinit var title: TextView
    private lateinit var appbar: AppBarLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rates, container, false)
        presenter = RatesPresenter(this)
        initViews(view)
        return view
    }

    override fun onDestroyView() {
        presenter?.onDestroyView()
        presenter = null
        super.onDestroyView()
    }

    override fun scrollToTop() {
        recycler.scrollToPosition(0)
        appbar.setExpanded(true, true)
    }

    private fun initViews(view: View) {
        recycler = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = presenter?.createAdapter()
        }
        title = view.findViewById(R.id.title)
        appbar = view.findViewById(R.id.appbar)
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