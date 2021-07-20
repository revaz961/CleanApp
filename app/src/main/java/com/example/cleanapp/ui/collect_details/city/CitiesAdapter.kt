package com.example.cleanapp.ui.collect_details.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.databinding.CityViewHolderBinding

class CitiesAdapter (private var cities: MutableList<String>, private val clickListener: CityClickListener) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {
    fun setItem(list:List<String>){
        cities.clear()
        cities.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = CityViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = cities.size

    inner class CityViewHolder(private val binding: CityViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: String
        fun bind() {
            model = cities[adapterPosition]
            binding.cityName.text = model
            binding.cityName.setOnClickListener {
                clickListener.onCityClick(model)
            }
        }
    }

    fun filterCities(filteredCities: MutableList<String>) {
        cities = filteredCities
        notifyDataSetChanged()
    }
}