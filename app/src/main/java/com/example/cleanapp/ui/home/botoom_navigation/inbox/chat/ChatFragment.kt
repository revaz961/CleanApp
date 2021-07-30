package com.example.cleanapp.ui.home.botoom_navigation.inbox.chat

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChatFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter
    private lateinit var chat: Chat


    override fun start() {
        chat = arguments?.getParcelable("chat")!!
        setListeners()
        initRecycler()
        observes()
        binding.tvTitle.text =
            if (viewModel.getCurrentUser().name == chat.memberOne)
                chat.memberTwo
            else
                chat.memberOne
    }

    private fun setListeners() {

        binding.btnSend.setOnClickListener {

            val user = viewModel.getCurrentUser()

            viewModel.sendMessage(
                Message(
                    binding.etMessage.text.toString(),
                    user.uid!!,
                    user.name!!,
                    user.imgUrl!!,
                    Date().time,
                    false
                ),
                chat.chatId
            )
            binding.etMessage.text.clear()
        }
    }

    private fun initRecycler() {
        adapter = ChatAdapter().apply {
            currentUserId = viewModel.getCurrentUserId()
            scrollTo = {
                binding.rvMessages.scrollToPosition(it)
            }

            onRead = {
                viewModel.checkAsRead(chat, it)
            }
        }

        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getMessages(chat.chatId)
    }

    private fun observes() {
        viewModel.messagesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
                    binding.progress.gone()
                }
                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.sendMessagesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    binding.progress.gone()
                }
                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.singleMessagesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.addItem(it.data!!)
                    binding.progress.gone()
                }
                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })
    }
}