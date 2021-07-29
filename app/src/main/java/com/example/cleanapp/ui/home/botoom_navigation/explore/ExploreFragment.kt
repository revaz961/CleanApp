package com.example.cleanapp.ui.home.botoom_navigation.explore

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ExploreFragmentBinding
import com.example.cleanapp.extensions.collapse
import com.example.cleanapp.extensions.expand
import com.example.cleanapp.models.*
import com.example.cleanapp.retrofit.ApiUtil
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : BaseFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private val sharedViewModel: ChooserViewModel by activityViewModels()

    override fun start() {
        binding.btnChooser.setOnClickListener {
            sharedViewModel.setFragmentTitle(getString(R.string.category_title))
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_homeFragment_to_chooserFragment)
        }
        binding.imageView5.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val not = NotificationSent("dkG68MyUS8-Dmozf11cP6_:APA91bEeE6DEZlcOkPTZNm_CaZvsyZR7WdWRfL8NvwV9Znk1kVKed08wEELI7Gip_FTtg62k131xAZghueBSGM7f8t_ePC4OqFi5gMt9ajfm_9tccnj8Vxmp9c3Srqy-bcTRtxMCk8ZL")
                    ApiUtil.apiService().sendNotification(not)
                }
            }
        }
    }
}