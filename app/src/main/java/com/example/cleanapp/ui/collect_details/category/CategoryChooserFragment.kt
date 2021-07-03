package com.example.cleanapp.ui.collect_details.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.ui.collect_details.city.CityChooserViewModel
import com.example.cleanapp.R
import com.example.cleanapp.databinding.CategoryChooserFragmentBinding

class CategoryChooserFragment : Fragment() {

    private var _binding: CategoryChooserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CityChooserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CategoryChooserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButton2.setOnClickListener {
            findNavController().navigate(R.id.action_categoryChooserFragment_to_cityChooserFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}