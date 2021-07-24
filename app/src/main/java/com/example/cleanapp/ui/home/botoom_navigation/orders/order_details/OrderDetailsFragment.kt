package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.ETC1.getWidth
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.OrderDetailsFragmentBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseFragment<OrderDetailsFragmentBinding>(OrderDetailsFragmentBinding::inflate) {

    private val viewModel: OrderDetailsViewModel by viewModels()
    private lateinit var order: Order
    private lateinit var master: Master

    override fun start() {
        order = arguments?.getParcelable("order")!!
        order.masterUid?.let { viewModel.getMaster(it) }
        initView()
        observes()
    }

    private fun initView() {

        with(binding) {
            when (order.status) {
                OrderStatusEnum.ONGOING.status -> {
                    tvOrderStatus.setTextById(R.string.ongoing)
                    tvOrderStatus.setTextColor(Color.GREEN)
                }
                OrderStatusEnum.FINISHED.status -> {
                    tvOrderStatus.setTextById(R.string.finished)
                    tvOrderStatus.setTextColor(Color.GRAY)
                }
                OrderStatusEnum.CANCELLED.status -> {
                    tvOrderStatus.setTextById(R.string.cancelled)
                    tvOrderStatus.setTextColor(Color.RED)
                }
            }

            master.user?.let {
                tvFullname.setTextById(
                    R.string.master_full_name,
                    it.name,
                    master.user!!.surname
                )
            }
            tvCatPrice.setResourceHtmlText(R.string.cat_price, order.categoryId, order.price)
            tvAddress.text = order.address

            imgAuthor.load(master.user?.imgUrl)
            tvRating.text = master.rating.toString()

            tvReservationDateValue.text = order.reservationDate?.toDateFormat("MMM DD") ?: "error"
            tvCleaningDayValue.text = order.date?.toDateFormat("MMM DD") ?: "error"
            tvCleaningTimeValue.text = order.date?.toDateFormat("h:mm a") ?: "error"

            val cleaningFee = order.price * order.duration!!
            val serviceFee = cleaningFee * 0.2
            val totalFee = cleaningFee + serviceFee
            tvCategoryFee.setResourceHtmlText(R.string.cleaning_fee, order.price, order.duration)
            tvServiceFeeValue.text = cleaningFee.toString()
            tvServiceFeeValue.text = serviceFee.toString()
            tvTotalValue.text = totalFee.toString()

            if (order.status == OrderStatusEnum.ONGOING.status) {
                tvCancelReservation.setOnClickListener {
                    cancelReservation(order)
                }
            } else {
                tvCancelReservation.gone()
            }
            btnSavePdf.setOnClickListener {
                savePdf(it)
            }
        }
    }

    private fun cancelReservation(order: Order) {
        viewModel.cancelReservation(order)
    }

    private fun observes() {

        order.masterUid?.let {
            viewModel.ordersDetailsLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        master = it.data!!
                    }
                }
            })
        }
    }

    private fun savePdf(view: View) {
        val bitmap = loadBitmap(binding.root, binding.root.width, binding.root.height)
    }

    private fun loadBitmap(layout: LinearLayout, width: Int, height: Int) {

    }
}