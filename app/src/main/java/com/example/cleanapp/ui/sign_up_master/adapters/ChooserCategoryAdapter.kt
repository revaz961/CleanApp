package com.example.cleanapp.ui.sign_up_master.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.ChooseCategoryViewHolderBinding
import com.example.cleanapp.extensions.load
import com.example.cleanapp.models.Category
import com.example.cleanapp.models.MasterCategory

class ChooserCategoryAdapter : BaseAdapter<MasterCategory>() {

    fun setItems(items:List<MasterCategory>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MasterCategory, ViewBinding> {
        return ViewHolder(
            ChooseCategoryViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: ChooseCategoryViewHolderBinding) :
        BaseViewHolder<MasterCategory, ChooseCategoryViewHolderBinding>(binding) {
        override fun bind(data: MasterCategory) {
            binding.checkCategory.text = data.category!!.categoryEn
            binding.checkCategory.isChecked = data.category.isChecked
            binding.editPrice.setText(data.price.toString())

            binding.editPrice.doOnTextChanged { text, start, before, count ->
                items[adapterPosition].price = text.toString().toFloat()
            }

            binding.checkCategory.setOnCheckedChangeListener { buttonView, isChecked ->
                items[adapterPosition].category!!.isChecked = isChecked
            }
        }
    }

}