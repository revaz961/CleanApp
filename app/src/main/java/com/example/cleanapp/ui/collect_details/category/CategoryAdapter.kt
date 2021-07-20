package com.example.cleanapp.ui.collect_details.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.CategoryViewHolderBinding
import com.example.cleanapp.extensions.load
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
    ): BaseViewHolder<ViewBinding> {
        return CategoryViewHolder(
            CategoryViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CategoryViewHolder(private val binding: CategoryViewHolderBinding) :
        BaseViewHolder<CategoryViewHolderBinding>(binding) {
        lateinit var data: Category
        override fun bind() {
            data = items[absoluteAdapterPosition]
            binding.tvCategory.text = data.categoryEn
            binding.ivCategory.load(data.imageUrl)

            binding.root.setOnClickListener {
                chooseCategory(data)
            }
        }
    }
}