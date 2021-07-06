package com.example.cleanapp.ui.collect_details.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.CategoryViewHolderBinding
import com.example.cleanapp.extension.load
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
    ): BaseViewHolder<Category, ViewBinding> {
        return CategoryViewHolder(
            CategoryViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CategoryViewHolder(private val binding: CategoryViewHolderBinding) :
        BaseViewHolder<Category, CategoryViewHolderBinding>(binding) {
        override fun bind(data: Category) {
            binding.tvCategory.text = data.category_en
            binding.ivCategory.load(data.image_url)

            binding.root.setOnClickListener {
                chooseCategory(data)
            }
        }
    }
}