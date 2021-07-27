package com.example.cleanapp.ui.home.botoom_navigation.orders

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.OrdersFragmentBinding
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<OrdersFragmentBinding>(OrdersFragmentBinding::inflate) {

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var adapter: OrdersAdapter

    override fun start() {
        initRecycler()
        observes()
    }

    private fun initRecycler() {
        adapter = OrdersAdapter().apply {
            chooseOrder = {
                findNavController().navigate(R.id.action_ordersFragment_to_orderDetailsFragment,
                    bundleOf("order" to it)
                )
            }
        }

        binding.rvOrders.adapter = adapter
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())

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