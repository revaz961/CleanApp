package com.example.cleanapp.ui.home.botoom_navigation.inbox

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.InboxFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.extensions.hide
import com.example.cleanapp.extensions.show
import com.example.cleanapp.models.Chat
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
            chooseChat = { chat ->
                chat.lastMessage?.let {
                    val userId = viewModel.getCurrentUser().uid
                    if (!it.isRead && it.senderId != userId)
                        viewModel.checkReadMessage(chat)
                }

                findNavController().navigate(
                    R.id.action_inboxFragment_to_chatFragment,
                    bundleOf("chat" to chat)
                )
            }
            viewModel.getCurrentUser().let {
                userId = it.uid!!
                userName = it.name!!
            }
        }

        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getChats()
    }

    private fun setUnreadMessageCount(chats: List<Chat>) {
        val count =
            chats.count {
                !it.lastMessage!!.isRead ?: false
                        && it.lastMessage!!.senderId != viewModel.getCurrentUser().uid
            }
        if (count > 0) {
            binding.tvNMessages.text = "$count"
            binding.tvNMessages.show()
        } else {
            binding.tvNMessages.text = "$count"
            binding.tvNMessages.hide()
        }
    }

    private fun observes() {
        viewModel.chatsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    setUnreadMessageCount(it.data!!)
                    adapter.setItems(it.data)
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

        viewModel.checkChatLiveData.observe(viewLifecycleOwner, {
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
    }
}
