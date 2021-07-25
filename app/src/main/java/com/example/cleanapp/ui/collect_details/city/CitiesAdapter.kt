package com.example.cleanapp.ui.collect_details.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanapp.databinding.CityViewHolderBinding
import com.example.cleanapp.models.City

typealias CityClick = (city : City) -> Unit

class CitiesAdapter (private var cities: MutableList<City>) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    lateinit var clickListener: CityClick

    fun setItem(list:List<City>){
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
        private lateinit var model: City
        fun bind() {
            model = cities[adapterPosition]
            binding.cityName.text = model.cityEn
            binding.cityName.setOnClickListener {
                clickListener(model)
            }
        }
    }

    fun filterCities(filteredCities: MutableList<City>) {
        cities = filteredCities
        notifyDataSetChanged()
    }
}