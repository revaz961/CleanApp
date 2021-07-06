package com.example.cleanapp.ui.collect_details.city

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.databinding.CityChooserFragmentBinding
import com.example.cleanapp.models.City
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CityChooserFragment : Fragment() {

    private var _binding: CityChooserFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CityChooserViewModel by viewModels()
    private val sharedViewModel: ChooserViewModel by activityViewModels()

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
        observes()
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
        adapter = CitiesAdapter(cities, object : CityClickListener {
            override fun onCityClick(city: String) {
                sharedViewModel.setCity(City(city))
                findNavController().navigate(R.id.action_cityChooserFragment_to_categoryChooserFragment)
            }

        })
        binding.recyclerCities.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerCities.adapter = adapter
        adapter.notifyDataSetChanged()
        viewModel.getCities()
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

    private fun observes(){
        viewModel.liveData.observe(viewLifecycleOwner,{
            when(it){
                is ResultHandler.Success -> adapter.setItem(it.data!!.map { it.city_en })
            }
        })
    }
}