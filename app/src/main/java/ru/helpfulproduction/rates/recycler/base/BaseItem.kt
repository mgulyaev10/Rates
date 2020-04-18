package ru.helpfulproduction.rates.recycler.base

import androidx.annotation.LayoutRes

abstract class BaseItem {
    @LayoutRes abstract fun getViewType(): Int
}