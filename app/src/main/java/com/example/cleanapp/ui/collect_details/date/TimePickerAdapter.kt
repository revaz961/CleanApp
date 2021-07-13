package com.example.cleanapp.ui.collect_details.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.databinding.TimePickerItemBinding

class TimePickerAdapter(private val timeIdentifier:Int) : RecyclerView.Adapter<TimePickerAdapter.TimePickerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimePickerViewHolder {
        return TimePickerViewHolder(
            TimePickerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimePickerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = Int.MAX_VALUE

    inner class TimePickerViewHolder(private val binding: TimePickerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.text = "${adapterPosition % timeIdentifier}"
        }
    }
}