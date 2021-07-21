package com.example.cleanapp.ui.home.master_reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.RecyclerMasterItemBinding
import com.example.cleanapp.models.Master

class MoreMastersAdapter : BaseAdapter<Master>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Master, ViewBinding> =
        MasterViewHolder(
            RecyclerMasterItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class MasterViewHolder(private val binding: RecyclerMasterItemBinding) :
        BaseViewHolder<Master, RecyclerMasterItemBinding>(binding) {
        override fun bind(data: Master) {

        }
    }
}