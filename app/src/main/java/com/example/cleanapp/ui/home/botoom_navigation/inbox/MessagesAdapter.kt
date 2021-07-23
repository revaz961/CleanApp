package com.example.cleanapp.ui.home.botoom_navigation.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.InboxFragmentViewholderBinding
import com.example.cleanapp.extensions.hide
import com.example.cleanapp.extensions.load
import com.example.cleanapp.extensions.show
import com.example.cleanapp.models.Chat

typealias ChooseMessage = (message: Chat)->Unit

class MessagesAdapter : BaseAdapter<Chat>() {

    lateinit var chooseMessage: ChooseMessage

    fun setItems(list: List<Chat>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Chat, ViewBinding> {
        return MessageViewHolder(
            InboxFragmentViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MessageViewHolder(private val binding: InboxFragmentViewholderBinding) :
        BaseViewHolder<Chat, InboxFragmentViewholderBinding>(binding) {
        override fun bind(data: Chat) {
            if (data.isRead) binding.imgRead.show() else binding.imgRead.hide()
            binding.imgMessageAuthor.load(data.lastMessageImage)
            binding.tvMessageAuthorName.text = data.lastMessageAuthor
            binding.tvMessageContent.text = data.lastMessage?.message
            binding.root.setOnClickListener {
                chooseMessage(data)
            }
        }
    }
}