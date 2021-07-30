package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.R
import com.example.cleanapp.databinding.VhReviewStarBinding

class ReviewStarAdapter : RecyclerView.Adapter<ReviewStarAdapter.StarViewHolder>() {
    companion object {
        const val MAX_STAR_COUNT = 5
    }

    var selectStar = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        return StarViewHolder(
            VhReviewStarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = MAX_STAR_COUNT

    inner class StarViewHolder(private val binding: VhReviewStarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            if (adapterPosition <= selectStar)
                binding.root.setImageResource(R.drawable.ic_star_fill)
            else
                binding.root.setImageResource(R.drawable.ic_star_outline)

            binding.root.setOnClickListener {
                selectStar = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}