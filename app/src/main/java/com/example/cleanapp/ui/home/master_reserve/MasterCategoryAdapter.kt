package com.example.cleanapp.ui.home.master_reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.VhReserve01CategoriesBinding
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.setTintColor
import com.example.cleanapp.models.MasterCategory

class MasterCategoryAdapter : BaseAdapter<MasterCategory>() {

    fun setItem(items:List<MasterCategory>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MasterCategory, ViewBinding> =
        MasterViewHolder(
            VhReserve01CategoriesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class MasterViewHolder(private val binding: VhReserve01CategoriesBinding) :
        BaseViewHolder<MasterCategory, VhReserve01CategoriesBinding>(binding) {
        override fun bind(data: MasterCategory) {
            data.category?.let {
                binding.categoryCircle.setTintColor(it.color)
                binding.tvCategory.text = it.categoryEn
            }
            binding.tvPrice.setResourceHtmlText(R.string.price,data.price)
        }
    }
}