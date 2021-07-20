package com.example.cleanapp.base


import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdapter<T>() :
    RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {

    protected val items = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = items.size
}