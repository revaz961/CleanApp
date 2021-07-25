package com.example.cleanapp.ui.home.confirmation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ConfirmationFragmentBinding
import com.example.cleanapp.databinding.OrdersFragmentBinding
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersAdapter
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersViewModel

class ConfirmationFragment : BaseFragment<ConfirmationFragmentBinding>(ConfirmationFragmentBinding::inflate) {

    private val viewModel: ConfirmationViewModel by viewModels()
    private lateinit var adapter: ConfirmationAdapter

    override fun start() {
        initRecycler()
        observes()
    }

    private fun initRecycler() {
        adapter = ConfirmationAdapter().apply {
            sendMessage = {
                findNavController().navigate(R.id.action_ordersFragment_to_orderDetailsFragment,
                    bundleOf("order" to it)
                )
            }
        }
        viewModel.getOrders()
    }

    private fun observes() {
        viewModel.ordersLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
                }
            }
        })
    }
}