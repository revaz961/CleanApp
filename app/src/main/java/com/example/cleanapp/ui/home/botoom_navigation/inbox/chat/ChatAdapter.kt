package com.example.cleanapp.ui.home.botoom_navigation.inbox.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.VhInboxDetailIncomingBinding
import com.example.cleanapp.databinding.VhInboxDetailOutgoingBinding
import com.example.cleanapp.extensions.loadFromStorage
import com.example.cleanapp.extensions.setTextById
import com.example.cleanapp.models.Message

typealias OnRead = (Message) -> Unit
typealias OnScroll = (Int) -> Unit

class ChatAdapter : BaseAdapter<Message>() {
    companion object {
        const val CURRENT_USER = 1
        const val OTHER_USER = 2
    }

    lateinit var currentUserId: String
    lateinit var scrollTo: OnScroll
    lateinit var onRead: OnRead

    fun setItems(list: List<Message>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
        scrollTo(items.size - 1)
    }

    fun addItem(message: Message) {
        items.add(message)
        notifyItemInserted(items.size - 1)
        scrollTo(items.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Message, ViewBinding> {
        return when (viewType) {
            CURRENT_USER -> OutgoingMessageViewHolder(
                VhInboxDetailOutgoingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> IncomingMessageViewHolder(
                VhInboxDetailIncomingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int) =
        if (items[position].senderId == currentUserId) CURRENT_USER else OTHER_USER


    inner class IncomingMessageViewHolder(private val binding: VhInboxDetailIncomingBinding) :
        BaseViewHolder<Message, VhInboxDetailIncomingBinding>(binding) {
        override fun bind(data: Message) {
            binding.imgMessageAuthor.loadFromStorage(data.senderImage)
            binding.tvMessage.text = data.message
            if (!data.isRead)
                onRead(data)
        }
    }

    inner class OutgoingMessageViewHolder(private val binding: VhInboxDetailOutgoingBinding) :
        BaseViewHolder<Message, VhInboxDetailOutgoingBinding>(binding) {
        override fun bind(data: Message) {
            binding.imgMessageAuthor.loadFromStorage(data.senderImage)
            binding.tvMessage.text = data.message
        }
    }
}