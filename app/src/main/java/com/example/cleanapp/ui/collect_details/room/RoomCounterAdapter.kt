package com.example.cleanapp.ui.collect_details.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.RoomCounterLayoutBinding
import com.example.cleanapp.models.RoomCounter

typealias ButtonClick = (position: Int) -> Int

class RoomCounterAdapter : BaseAdapter<RoomCounter>() {

    lateinit var increaseClick:ButtonClick
    lateinit var decreaseClick:ButtonClick

    fun setItems(list: List<RoomCounter>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RoomCounter, ViewBinding> {
        return CounterViewHolder(
            RoomCounterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CounterViewHolder(private val binding: RoomCounterLayoutBinding) :
        BaseViewHolder<RoomCounter, RoomCounterLayoutBinding>(binding) {
        override fun bind(data: RoomCounter) {
            binding.tvRoom.text = data.room
            binding.tvCount.text = data.count.toString()

            binding.btnAdd.setOnClickListener {
                binding.tvCount.text = "${increaseClick(adapterPosition)}"
            }

            binding.btnRemove.setOnClickListener {
                binding.tvCount.text = "${decreaseClick(adapterPosition)}"
            }
        }
    }
}