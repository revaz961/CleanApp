package com.example.cleanapp.ui.home.botoom_navigation.orders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.OrdersFragmentVhBinding
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.setTextById
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Order
import com.example.cleanapp.utils.OrderStatusEnum

typealias ChooseOrder = (order: Order) -> Unit

class OrdersAdapter : BaseAdapter<Order>() {

    lateinit var chooseOrder: ChooseOrder

    fun setItems(list: List<Order>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Order, ViewBinding> {
        return OrderViewHolder(
            OrdersFragmentVhBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class OrderViewHolder(private val binding: OrdersFragmentVhBinding) :
        BaseViewHolder<Order, OrdersFragmentVhBinding>(binding) {
        override fun bind(data: Order) {

            binding.root.setOnClickListener {
                chooseOrder(data)
            }

            val category = data.category?.categoryEn?: "category not found"
            val date = data.date?.toDateFormat("MMMM dd") ?: "####"
            binding.tvCatDate.setTextById(R.string.category_date, category, date)

            with(binding.tvPrice) {
                when (data.status) {
                    OrderStatusEnum.CANCELLED.status -> {
                        setTextById(R.string.cancelled)
                        setTextColor(Color.RED)
                    }
                    OrderStatusEnum.FINISHED.status -> {
                        setTextById(R.string.finished)
                        binding.tvPrice.setTextColor(Color.GRAY)
                    }
                    OrderStatusEnum.ONGOING.status -> {
                        setTextById(R.string.ongoing)
                        binding.tvPrice.setTextColor(Color.GREEN)
                    }
                }
            }
        }
    }
}