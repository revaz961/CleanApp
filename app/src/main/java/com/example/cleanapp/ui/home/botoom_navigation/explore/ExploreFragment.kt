package com.example.cleanapp.ui.home.botoom_navigation.explore

import androidx.fragment.app.activityViewModels
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
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

class ExploreFragment : BaseFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private val sharedViewModel: ChooserViewModel by activityViewModels()

    override fun start() {
        binding.btnChooser.setOnClickListener {
            sharedViewModel.setFragmentTitle(getString(R.string.category_title))
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_homeFragment_to_chooserFragment)
        }
    }
}