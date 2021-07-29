package com.example.cleanapp.ui.home.master_reserve.aditional_actions.cancellation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CancellationFragmentBinding
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.extensions.toDateFormatMinusDays
import com.example.cleanapp.models.Master

class CancellationFragment :
    BaseFragment<CancellationFragmentBinding>(CancellationFragmentBinding::inflate) {

    override fun start() {
        val days =  arguments?.getInt("days")
        val date = arguments?.getLong("date")

        val fullRefDate = days?.let { date!!.toLong().toDateFormatMinusDays("MMM dd", it) }

        binding.tvDateFullRefund.text = fullRefDate
        if (date != null) {
            binding.tvDateSemiRefund.text = date.toLong().toDateFormat("MMM dd")
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}