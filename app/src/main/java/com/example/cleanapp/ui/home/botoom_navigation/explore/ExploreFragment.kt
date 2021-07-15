package com.example.cleanapp.ui.home.botoom_navigation.explore

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ExploreFragmentBinding
import com.example.cleanapp.extensions.collapse
import com.example.cleanapp.extensions.expand

class ExploreFragment : BaseFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {
    override fun start() {
        binding.btnChooser.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_chooserFragment)
        }

        binding.rvMaster.adapter = MasterAdapter()
        binding.rvMaster.layoutManager = LinearLayoutManager(requireContext())
    }
}