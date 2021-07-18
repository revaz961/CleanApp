package com.example.cleanapp.ui.home.botoom_navigation.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseAdapter
import com.example.cleanapp.base.BaseViewHolder
import com.example.cleanapp.databinding.RecyclerMasterItemBinding
import com.example.cleanapp.extensions.collapse
import com.example.cleanapp.extensions.expand
import com.example.cleanapp.extensions.setResourceHtmlText
import com.example.cleanapp.extensions.toDateFormat
import com.example.cleanapp.models.Master

class MasterAdapter :
    BaseAdapter<Master>() {

    fun addItem(master: Master) {
        items.add(master)
        notifyItemInserted(items.size - 1)
    }

    fun setItems(items: List<Master>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Master, ViewBinding> {
        return ViewHolder(
            RecyclerMasterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class ViewHolder(private val binding: RecyclerMasterItemBinding) :
        BaseViewHolder<Master, RecyclerMasterItemBinding>(binding) {

        override fun bind(data: Master) {

            binding.tvDescription.text = data.reviews?.comments?.fold("") { acc, review ->
                "$acc \n ${review.dateAt?.toDateFormat("MMMM YYYY")} ${review.comment}\n"
            }?.trim() ?: ""

            binding.tvMaster.setResourceHtmlText(
                R.string.reviews2,
                data.rating!!,
                data.nReviews,
                data.name ?: ""
            )

            data.categories?.get(0)?.let {
                binding.tvPrice.setResourceHtmlText(
                    R.string.price,
                    it.price
                )
            }

            binding.ivMaster.setOnClickListener {
                binding.tvDescription.collapse()
            }

            binding.ivStar.setOnClickListener {
                binding.tvDescription.expand()
            }
        }
    }
}