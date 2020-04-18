package ru.helpfulproduction.rates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.RatesContract
import ru.helpfulproduction.rates.RatesPresenter
import ru.helpfulproduction.rates.currency.CurrenciesAdapter
import ru.helpfulproduction.rates.mvp.BaseMvpFragment

class RatesFragment: BaseMvpFragment<RatesContract.Presenter>(),
    RatesContract.View<RatesContract.Presenter> {

    private lateinit var recycler: RecyclerView
    private lateinit var title: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rates, container, false)
        initViews(view)
        presenter = RatesPresenter(this)
        return view
    }

    override fun onDestroyView() {
        presenter = null
        super.onDestroyView()
    }

    private fun initViews(view: View) {
        recycler = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CurrenciesAdapter()
        }
        title = view.findViewById(R.id.title)
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