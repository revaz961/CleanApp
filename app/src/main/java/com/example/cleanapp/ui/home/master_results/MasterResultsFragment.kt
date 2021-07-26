package com.example.cleanapp.ui.home.master_results

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.MasterResultsFragmentBinding
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MasterResultsFragment :
    BaseFragment<MasterResultsFragmentBinding>(MasterResultsFragmentBinding::inflate) {

    private val masterResultsViewModel: MasterResultsViewModel by viewModels()
    private lateinit var masterAdapter: MasterAdapter
    private lateinit var order: Order

    override fun start() {
        order = arguments?.getParcelable<Order>("order")!!
        observes()
        initRecycler()
    }

    private fun initRecycler() {
        masterAdapter = MasterAdapter(object : MasterClickListener {
            override fun onClick(master: Master) {

                val arr = ArrayList(masterResultsViewModel.masters)
                findNavController().navigate(
                    R.id.action_masterResultsFragment_to_masterReserveFragment,
                    bundleOf(
                        "master" to master,
                        "order" to order,
                        "moreMasters" to arr
                    )
                )
            }

        })
        binding.rvMaster.adapter = masterAdapter
        binding.rvMaster.layoutManager = LinearLayoutManager(requireContext())
        val query = "${order.city?.cityEn?.lowercase()}_${
            order.category?.categoryEn?.lowercase()?.replace(' ', '_')
        }"
        masterResultsViewModel.getMaster(query)
    }

    private fun observes() {
        masterResultsViewModel.exploreLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> masterAdapter.setItems(it.data!!)

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {}
            }
        })
    }
}