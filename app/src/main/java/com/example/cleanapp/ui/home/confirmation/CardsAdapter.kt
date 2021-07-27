package com.example.cleanapp.ui.home.confirmation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.VhConfirm31VhCardBinding
import com.example.cleanapp.extensions.setBorder
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.setTextById
import com.example.cleanapp.models.Card

class CardsAdapter : BaseAdapter<Card>() {

    lateinit var selectCard: (Card) -> Unit

    fun setItem(items: List<Card>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Card, ViewBinding> =
        CardViewHolder(
            VhConfirm31VhCardBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class CardViewHolder(private val binding: VhConfirm31VhCardBinding) :
        BaseViewHolder<Card, VhConfirm31VhCardBinding>(binding) {
        override fun bind(data: Card) {

            binding.tvCardDigits.setTextById(R.string.card_digits, data.cardNumber.takeLast(4))
            binding.tvTitle.setResourceHtmlText(R.string.edit)
            binding.root.setOnClickListener {
                selectCard(data)
                it.setBorder(2, "#000000",0f)
            }

            when (data.cardNumber[0]) {

            }
//            binding.imgCard.


        }
    }
}