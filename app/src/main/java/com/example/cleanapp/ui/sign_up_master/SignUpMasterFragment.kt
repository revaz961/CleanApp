package com.example.cleanapp.ui.sign_up_master

import android.app.Dialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChooserCityDialogBinding
import com.example.cleanapp.databinding.SignUpMasterFragmentBinding
import com.example.cleanapp.extensions.init
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpMasterFragment :
    BaseFragment<SignUpMasterFragmentBinding>(SignUpMasterFragmentBinding::inflate) {

    private val viewModel: SignUpMasterViewModel by viewModels()
    private lateinit var cityAdapter: ChooserCityAdapter

    override fun start() {
        observers()
        initAdapters()
        binding.editCity.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showCityChooserDialog()
        }
    }

    private fun initAdapters() {
        cityAdapter = ChooserCityAdapter()
    }

    private fun showCityChooserDialog() {
        viewModel.getCities()
        val dialogBinding = ChooserCityDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.init(dialogBinding.root)
        cityAdapter.onClick = {
            binding.editCity.setText(it.cityEn)
            dialog.cancel()
            binding.editCity.clearFocus()
        }
        dialogBinding.rvCityChooser.adapter = cityAdapter
        dialogBinding.rvCityChooser.layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.btnChoose.setOnClickListener {
            dialog.cancel()
            binding.editCity.clearFocus()
        }
        dialog.show()
    }

    private fun observers() {
        viewModel.citiesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> cityAdapter.setItems(it.data!!)
            }
        })
    }

}