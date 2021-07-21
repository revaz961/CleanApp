package com.example.cleanapp.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolderType< out VB : ViewBinding>(binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind()
}