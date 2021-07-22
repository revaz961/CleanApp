package com.example.cleanapp.ui.collect_details.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.CategoryViewHolderBinding
import com.example.cleanapp.extensions.load
import com.example.cleanapp.extensions.setBorder
import com.example.cleanapp.extensions.textColor
import com.example.cleanapp.models.Category

typealias ChooseCategory = (category:Category)->Unit

class CategoryAdapter : BaseAdapter<Category>() {

    lateinit var chooseCategory:ChooseCategory

    fun setItems(list:List<Category>){
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Category,ViewBinding> {
        return CategoryViewHolder(
            CategoryViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CategoryViewHolder(private val binding: CategoryViewHolderBinding) :
        BaseViewHolder<Category,CategoryViewHolderBinding>(binding) {
        override fun bind(data: Category) {
            binding.tvCategory.text = data.categoryEn
            binding.tvCategory.textColor(data.color)
            binding.ivCategory.load(data.imageUrl)
            binding.root.setBorder(5,data.color,50f)

            binding.root.setOnClickListener {
                chooseCategory(data)
            }
        }
    }
}