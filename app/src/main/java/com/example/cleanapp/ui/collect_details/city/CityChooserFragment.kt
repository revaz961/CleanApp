package com.example.cleanapp.ui.collect_details.city

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.GeneratedAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.databinding.CityChooserFragmentBinding
import java.util.*

class CityChooserFragment : Fragment() {

    private var _binding: CityChooserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CityChooserViewModel

    private val cities = mutableListOf("Tbilisi", "Kutaisi", "Batumi", "Borjomi", "Gori", "Rustavi")
    private lateinit var adapter: CitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CityChooserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        binding.inputCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }

    private fun initRecycler() {
        adapter = CitiesAdapter(cities)
        binding.recyclerCities.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerCities.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun filter(input: String) {
        val filteredCities = mutableListOf<String>()
        for (city in cities) {
            if (city.lowercase(Locale.getDefault())
                    .contains(input.lowercase(Locale.getDefault()))
            ) {
                filteredCities.add(city)
            }
        }

        adapter.filterCities(filteredCities)
    }

}