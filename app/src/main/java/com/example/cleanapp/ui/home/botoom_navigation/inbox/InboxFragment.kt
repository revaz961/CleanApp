package com.example.cleanapp.ui.home.botoom_navigation.inbox

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.InboxFragmentBinding
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getChats()
    }

    private fun observes() {
        viewModel.chatsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
                }
                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {}
            }
        })
    }
}
