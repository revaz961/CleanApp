package com.example.cleanapp.ui.home.botoom_navigation.inbox.chat

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChatFragmentBinding
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter
    private lateinit var chat: Chat


    override fun start() {
        chat = arguments?.getParcelable("chat")!!
        initRecycler()
        observes()
    }

    private fun initRecycler(){
        adapter = ChatAdapter().apply {
            currentUserId = viewModel.getCurrentUserId()
        }

        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getMessages(chat.chatId)
    }

    private fun observes(){
        viewModel.messagesLiveData.observe(viewLifecycleOwner, {
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