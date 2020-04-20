package ru.helpfulproduction.rates.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.helpfulproduction.rates.R
import ru.helpfulproduction.rates.ui.fragments.RatesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openRatesFragment(savedInstanceState)
    }

    private fun openRatesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = getRatesFragment()
            val tag = RatesFragment.TAG
            openFragment(fragment, tag)
        }
    }

    private fun getRatesFragment(): Fragment {
        return RatesFragment.Builder()
            .build()
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, tag)
            .commit()
    }
}
