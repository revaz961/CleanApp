package com.example.cleanapp.ui.sign_up_master

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.CityViewHolderBinding
import com.example.cleanapp.models.City

class ChooserCityAdapter : BaseAdapter<City>() {

    fun setItems(list: List<City>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    lateinit var onClick: (City) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<City, ViewBinding> {
        return CityViewHolder(
            CityViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CityViewHolder(private val binding: CityViewHolderBinding) :
        BaseViewHolder<City, CityViewHolderBinding>(binding) {
        override fun bind(data: City) {
            binding.cityName.text = data.cityEn
            binding.cityName.setTextColor(Color.WHITE)
            binding.root.setOnClickListener {
                onClick(items[absoluteAdapterPosition])
            }
        }
    }
}