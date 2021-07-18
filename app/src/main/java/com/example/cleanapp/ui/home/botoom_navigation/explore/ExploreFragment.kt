package com.example.cleanapp.ui.home.botoom_navigation.explore

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ExploreFragmentBinding
import com.example.cleanapp.extensions.collapse
import com.example.cleanapp.extensions.expand
import com.example.cleanapp.models.Category
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var masterAdapter: MasterAdapter

    override fun start() {
        observes()
        initRecycler()
        binding.btnChooser.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_homeFragment_to_chooserFragment)
        }
    }

    private fun initRecycler() {
        masterAdapter = MasterAdapter()
        binding.rvMaster.adapter = masterAdapter
        binding.rvMaster.layoutManager = LinearLayoutManager(requireContext())
        exploreViewModel.getMaster()
    }

    private fun observes() {
        exploreViewModel.exploreLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> masterAdapter.setItems(it.data!!)
            }
        })
    }
}