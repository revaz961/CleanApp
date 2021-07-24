package com.example.cleanapp.ui.sign_up_master.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.ChooseCategoryViewHolderBinding
import com.example.cleanapp.extensions.load
import com.example.cleanapp.models.Category

class ChooserCategoryAdapter : BaseAdapter<Category>() {

    fun setItems(items:List<Category>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Category, ViewBinding> {
        return ViewHolder(
            ChooseCategoryViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: ChooseCategoryViewHolderBinding) :
        BaseViewHolder<Category, ChooseCategoryViewHolderBinding>(binding) {
        override fun bind(data: Category) {
            binding.checkCategory.text = data.categoryEn
            binding.checkCategory.isChecked = data.isChecked
            binding.checkCategory.setOnCheckedChangeListener { buttonView, isChecked ->
                items[absoluteAdapterPosition].isChecked = isChecked
            }
        }
    }

}