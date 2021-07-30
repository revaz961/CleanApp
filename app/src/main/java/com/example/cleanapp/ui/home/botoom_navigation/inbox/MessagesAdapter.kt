package com.example.cleanapp.ui.home.botoom_navigation.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.VhInboxBinding
import com.example.cleanapp.extensions.loadFromStorage
import com.example.cleanapp.extensions.showIf
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Chat

typealias ChooseChat = (message: Chat) -> Unit

class MessagesAdapter : BaseAdapter<Chat>() {

    lateinit var chooseChat: ChooseChat
    lateinit var userId: String
    lateinit var userName: String

    fun setItems(list: List<Chat>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Chat, ViewBinding> {
        return MessageViewHolder(
            VhInboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MessageViewHolder(private val binding: VhInboxBinding) :
        BaseViewHolder<Chat, VhInboxBinding>(binding) {
        override fun bind(data: Chat) {
            binding.imgRead.showIf(
                !data.lastMessage!!.isRead && data.lastMessage!!.senderId != userId
            )
            binding.imgMessageAuthor.loadFromStorage(data.lastMessage!!.senderImage)
            binding.tvMessageAuthorName.text =
                if (data.memberOne != userName) data.memberOne else data.memberTwo
            binding.tvMessageContent.text = data.lastMessage?.message
            binding.tvDate.text = data.lastMessage!!.timestamp!!.toDateFormat("MMM dd hh:mm a")
            binding.root.setOnClickListener {
                chooseChat(data)
            }
        }
    }
}