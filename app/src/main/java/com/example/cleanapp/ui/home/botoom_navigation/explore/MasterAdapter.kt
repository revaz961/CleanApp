package com.example.cleanapp.ui.home.botoom_navigation.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.databinding.RecyclerMasterItemBinding
import com.example.cleanapp.extensions.collapse
import com.example.cleanapp.extensions.expand

class MasterAdapter : RecyclerView.Adapter<MasterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RecyclerMasterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 10

    inner class ViewHolder(private val binding: RecyclerMasterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            binding.ivMaster.setOnClickListener {
                binding.tvDescription.collapse()
            }

            binding.ivStar.setOnClickListener {
                binding.tvDescription.expand()
            }
        }
    }
}