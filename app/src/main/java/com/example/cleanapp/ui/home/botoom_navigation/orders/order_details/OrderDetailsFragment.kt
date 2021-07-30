package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import android.app.Dialog
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.OrderDetailsFragmentBinding
import com.example.cleanapp.databinding.ReviewDialogBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Comment
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.utils.OrderStatusEnum
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseFragment<OrderDetailsFragmentBinding>(OrderDetailsFragmentBinding::inflate) {

    private val viewModel: OrderDetailsViewModel by viewModels()
    private lateinit var order: Order
    private lateinit var master: Master
    private var starAdapter = ReviewStarAdapter()

    override fun start() {
        order = arguments?.getParcelable("order")!!
        order.masterUid?.let { viewModel.getMaster(it) }
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_global_homeFragment)
        }

        binding.tvAddReview.setOnClickListener {
            showReviewDialog()
        }
    }

    private fun showReviewDialog() {
        val reviewDialog = Dialog(requireContext())
        val dialogBinding = ReviewDialogBinding.inflate(layoutInflater)
        reviewDialog.init(dialogBinding.root)

        dialogBinding.btnClose.setOnClickListener {
            reviewDialog.cancel()
        }

        dialogBinding.rvStars.adapter = starAdapter
        dialogBinding.rvStars.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        dialogBinding.btnAddReview.setOnClickListener {
            if (starAdapter.selectStar < 0 || dialogBinding.etComment.text.isEmpty())
                return

            val user = viewModel.getCurrentUser()
            val comment = Comment(
                dialogBinding.etComment.text.toString(),
                user.name,
                user.imgUrl,
                Date().time,
            )
            val realStars = starAdapter.selectStar + 1
            viewModel.addReview(comment, realStars, user, master, order)
            reviewDialog.cancel()
        }

        reviewDialog.show()

    }

    private fun initView() {
        viewModel.isReviewAdd(master.user!!.uid!!, order.orderId!!)

        with(binding) {
            when (order.status) {
                OrderStatusEnum.ONGOING.status -> {
                    tvOrderStatus.setTextById(R.string.ongoing)
                    tvOrderStatus.setTextColor(resources.getColor(R.color.text_green))
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
            tvCatPrice.setResourceHtmlText(
                R.string.cat_price,
                order.category?.categoryEn,
                order.price
            )
            tvAddress.text = order.address

            imgAuthor.loadFromStorage(master.user?.imgUrl!!)
            tvRating.text = master.rating?.toString() ?: "0.0"

            tvReservationDateValue.text = order.reservationDate?.toDateFormat("MMM dd") ?: "error"
            tvCleaningDayValue.text = order.date?.toDateFormat("MMM dd") ?: "error"
            tvCleaningTimeValue.text = order.date?.toDateFormat("h:mm a") ?: "error"

            tvCategoryFee.setResourceHtmlText(
                R.string.cleaning_fee,
                order.price,
                order.duration!!.minuteToHoursFloat()
            )

            tvServiceFeeValue.text = order.getServiceFee().roundToDecimal().toString()
            tvCategoryFeeValue.text = order.getCleaningPrice().roundToDecimal().toString()
            tvTotalValue.text = order.getTotalPrice().roundToDecimal().toString()

            if (order.status == OrderStatusEnum.ONGOING.status) {
                tvAddReview.gone()
                tvCancelReservation.setOnClickListener {
                    cancelReservation(order)
                }
            } else {
                tvCancelReservation.gone()
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
                        initView()
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })

            viewModel.orderStatusLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        order.status = it.data!!
                        initView()
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })

            viewModel.reviewAddLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        binding.tvAddReview.gone()
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })

            viewModel.isReviewAddLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultHandler.Success -> {
                        binding.tvAddReview.goneIf(it.data!! || order.status == OrderStatusEnum.ONGOING.status)
                        binding.progress.gone()
                    }

                    is ResultHandler.Error -> {
                        showErrorDialog(it.message)
                        binding.progress.gone()
                    }

                    is ResultHandler.Loading -> {
                        binding.progress.goneIf(!it.loading)
                    }
                }
            })
        }
    }
}