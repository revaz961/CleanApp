package com.example.cleanapp.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapterViewType<T> :
    RecyclerView.Adapter<BaseViewHolderType<ViewBinding>>() {

    protected val items = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseViewHolderType<ViewBinding>, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position] as Int
    }

    override fun getItemCount() = items.size
}