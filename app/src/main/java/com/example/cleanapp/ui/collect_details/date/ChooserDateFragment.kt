package com.example.cleanapp.ui.collect_details.date

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.cleanapp.R
import com.example.cleanapp.databinding.ChooserDateFragmentBinding
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChooserDateFragment : BaseFragment<ChooserDateFragmentBinding>(ChooserDateFragmentBinding::inflate) {
    private val calendar:Calendar by lazy{
        Calendar.getInstance()
    }

    private val viewModel: ChooserDateViewModel by viewModels()
    private val chooserViewModel: ChooserViewModel by activityViewModels()
    private lateinit var hourAdapter:TimePickerAdapter
    private lateinit var minuteAdapter:TimePickerAdapter

    override fun start() {
        initRecyclers()
        observes()
        setListeners()
    }

    private fun setListeners(){
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {
            chooserViewModel.setDate(calendar.timeInMillis)
            findNavController().navigate(R.id.action_chooserDateFragment2_to_roomChooserFragment,
                bundleOf("date" to calendar.timeInMillis))
        }

        binding.Calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
        }
    }

    private fun initRecyclers(){
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

    private fun observes(){
    }

}