package com.example.cleanapp.ui.collect_details.date

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChooserDateFragmentBinding
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.RoomCounter
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChooserDateFragment :
    BaseFragment<ChooserDateFragmentBinding>(ChooserDateFragmentBinding::inflate) {
    private val calendar: Calendar by lazy {
        Calendar.getInstance()
    }

    private val viewModel: ChooserDateViewModel by viewModels()
    private val chooserViewModel: ChooserViewModel by activityViewModels()
    private lateinit var hourAdapter: TimePickerAdapter
    private lateinit var minuteAdapter: TimePickerAdapter

    override fun start() {
        initRecyclers()
        observes()
        setListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {

            val order: Order = arguments?.getParcelable("order") ?: Order()
            val hour =
                (binding.rvHour.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() % 12

            val minute =
                (binding.rvMinute.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() % 60

            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            order.date = calendar.timeInMillis


            chooserViewModel.setFragmentTitle(order.date!!.toDateFormat("MMMM dd, hh:mm aaa"))

            if (order.category?.categoryEn != "Garden")
                findNavController().navigate(
                    R.id.action_chooserDateFragment_to_roomChooserFragment,
                    bundleOf("order" to order)
                )
            else{
                order.roomCount = listOf(RoomCounter("Garden",1))
                order.duration = order.category?.categoryDuration
                findNavController().navigate(
                    R.id.action_chooserDateFragment_to_cityChooserFragment,
                    bundleOf("order" to order)
                )
            }
        }

        binding.Calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }

    }

    private fun initRecyclers() {
        hourAdapter = TimePickerAdapter(24)
        binding.rvHour.adapter = hourAdapter
        binding.rvHour.layoutManager = LinearLayoutManager(requireContext())

        val hourLinearSnapHelper = LinearSnapHelper()
        hourLinearSnapHelper.attachToRecyclerView(binding.rvHour)

        minuteAdapter = TimePickerAdapter(60)
        binding.rvMinute.adapter = minuteAdapter
        binding.rvMinute.layoutManager = LinearLayoutManager(requireContext())

        val minuteLinearSnapHelper = LinearSnapHelper()
        minuteLinearSnapHelper.attachToRecyclerView(binding.rvMinute)
    }

    private fun observes() {
    }
}


//        binding.root.setOnTouchListener { v, event ->
//            var recentY = 0f
//
//            d("onTouchEvent","Y: ${event.rawY}")
//            when(event.actionMasked){
//                MotionEvent.ACTION_DOWN ->{
//                    recentY = event.rawY
//                    d("onTouchEvent","X: ${event.rawX}")
//                    d("onTouchEvent","Y: ${event.rawY}")
//                }
//
//                MotionEvent.ACTION_MOVE ->{
//                    d("onTouchEvent","recentY: ${recentY}")
//                    val dY = recentY - event.rawY
//                    recentY = event.rawY
//                    d("onTouchEvent","dY: ${dY}")
//                    d("onTouchEvent","Y: ${event.rawY}")
//                }
//
//                MotionEvent.ACTION_UP ->{
//
//                    d("onTouchEvent","X: ${event.rawX}")
//                    d("onTouchEvent","Y: ${event.rawY}")
//                }
//            }
//            true
//        }