package com.example.cleanapp.ui.home.botoom_navigation.inbox

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.CategoryViewHolderBinding
import com.example.cleanapp.databinding.InboxFragmentViewholderBinding
import com.example.cleanapp.extensions.*
import com.example.cleanapp.models.Category

typealias ChooseMessage = (message: Message)->Unit

class MessagesAdapter : BaseAdapter<Category>() {

    lateinit var chooseMessage: ChooseMessage

    fun setItems(list: List<Message>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Message, ViewBinding> {
        return MessageViewHolder(
            InboxFragmentViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MessageViewHolder(private val binding: InboxFragmentViewholderBinding) :
        BaseViewHolder<Message, InboxFragmentViewholderBinding>(binding) {
        override fun bind(data: Message) {
            if (data.read) binding.imgRead.show() else binding.imgRead.hide()
//            binding.imgMessageAuthor.load(data.)
            binding.tvMessageAuthorName.text = data.name
            binding.tvMessageContent.text = data.last_message

            binding.root.setOnClickListener {
                chooseMessage(data)
            }
        }
    }
}