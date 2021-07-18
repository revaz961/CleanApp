package com.example.cleanapp.ui.home.master_results

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.MasterResultsFragmentBinding
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.explore.MasterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasterResultsFragment :
    BaseFragment<MasterResultsFragmentBinding>(MasterResultsFragmentBinding::inflate) {

    private val masterResultsViewModel: MasterResultsViewModel by viewModels()
    private lateinit var masterAdapter: MasterAdapter

    override fun start() {
        observes()
        initRecycler()
    }

    private fun initRecycler() {
        masterAdapter = MasterAdapter()
        binding.rvMaster.adapter = masterAdapter
        binding.rvMaster.layoutManager = LinearLayoutManager(requireContext())
        masterResultsViewModel.getMaster("tbilisi_garden")
    }

    private fun observes() {
        masterResultsViewModel.exploreLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> masterAdapter.addItem(it.data!!)
            }
        })
    }
}