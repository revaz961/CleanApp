package com.example.cleanapp.ui.sign_up_master.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.LanguageViewHolderBinding

class ChooserLanguageAdapter : BaseAdapter<Pair<String, Boolean>>() {

    lateinit var onCheckLanguage: (position: Int, Pair<String, Boolean>) -> Unit

    fun setItems(items: List<Pair<String, Boolean>>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Pair<String, Boolean>, ViewBinding> {
        return ViewHolder(
            LanguageViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: LanguageViewHolderBinding) :
        BaseViewHolder<Pair<String, Boolean>, LanguageViewHolderBinding>(binding) {
        override fun bind(data: Pair<String, Boolean>) {
            binding.checkLanguage.text = data.first
            binding.checkLanguage.isChecked = data.second
            binding.checkLanguage.setOnCheckedChangeListener { buttonView, isChecked ->
                items[absoluteAdapterPosition] = Pair(data.first, isChecked)
                onCheckLanguage(absoluteAdapterPosition, items[absoluteAdapterPosition])
            }
        }
    }
}