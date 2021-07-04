package com.example.cleanapp.ui.collect_details.date

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.databinding.ChooserDateFragmentBinding
import com.example.cleanapp.base.BaseFragment
import java.util.*

class ChooserDateFragment : BaseFragment<ChooserDateFragmentBinding>(ChooserDateFragmentBinding::inflate) {
    private val calendar:Calendar by lazy{
        Calendar.getInstance()
    }

    override fun start() {
        init()
    }

    private fun init(){
        setListeners()
    }

    private fun setListeners(){
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_chooserDateFragment2_to_roomChooserFragment,
                bundleOf("date" to calendar.timeInMillis))
        }

        binding.Calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
        }
    }

}