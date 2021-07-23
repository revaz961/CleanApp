package com.example.cleanapp.ui.home.botoom_navigation.inbox

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CityChooserFragmentBinding
import com.example.cleanapp.databinding.FragmentReserveBinding
import com.example.cleanapp.databinding.InboxFragmentBinding
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import com.example.cleanapp.ui.collect_details.category.CategoryAdapter
import com.example.cleanapp.ui.collect_details.category.CategoryChooserViewModel
import com.example.cleanapp.ui.collect_details.city.CityChooserViewModel
import com.example.cleanapp.ui.home.master_reserve.MasterReserveAdapter

class InboxFragment : BaseFragment<InboxFragmentBinding>(InboxFragmentBinding::inflate) {

    private val viewModel: InboxViewModel by viewModels()
    private lateinit var adapter: MessagesAdapter

    override fun start() {
        initRecycler()
        observes()
    }

    private fun initRecycler() {
        adapter = MessagesAdapter().apply {
            chooseMessage = {

                findNavController().navigate(R.id.action_inboxFragment_to_chatFragment,
                    bundleOf("chat" to it)
                )

            }
        }

        viewModel.getChats()

    }

    private fun observes() {
        viewModel.chatsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
                }
            }
        })
    }

}
