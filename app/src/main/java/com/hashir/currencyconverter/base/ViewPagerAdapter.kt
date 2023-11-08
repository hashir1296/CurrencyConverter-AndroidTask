package com.hashir.currencyconverter.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hashir.currencyconverter.presentation.charts.ChartFragment
import com.hashir.currencyconverter.presentation.rates.RatesFragment


const val NUM_OF_PAGES = 2

class ViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                RatesFragment.newInstance()
            }

            1 -> {
                ChartFragment.newInstance()
            }
            else -> {
                RatesFragment.newInstance()
            }
        }
    }
}