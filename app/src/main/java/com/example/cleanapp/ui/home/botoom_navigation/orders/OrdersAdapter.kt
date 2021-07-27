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

            val category = data.category!!.categoryEn
            val date = data.date!!.toDateFormat("MMMM DD")
            binding.tvCatDate.setTextById(R.string.category_date, category, date)

            with(binding.tvPrice) {
                when (data.status) {
                    OrderStatusEnum.CANCELLED.status -> {
                        setTextById(R.string.cancelled)
                        setTextColor(Color.RED)
                    }
                    OrderStatusEnum.FINISHED.status -> {
                        setResourceHtmlText(R.string.price_value, data.price)
                        binding.tvPrice.setTextColor(Color.GRAY)
                    }
                    OrderStatusEnum.ONGOING.status -> {
                        setResourceHtmlText(R.string.price_value, data.price)
                        binding.tvPrice.setTextColor(Color.GREEN)
                    }
                }
            }
        }
    }
}