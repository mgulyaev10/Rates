package ru.helpfulproduction.rates.recycler.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T: BaseItem>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}