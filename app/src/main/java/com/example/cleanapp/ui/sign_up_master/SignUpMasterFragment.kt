package com.example.cleanapp.ui.sign_up_master

import android.app.Dialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChooserDialogBinding
import com.example.cleanapp.databinding.SignUpMasterFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.extensions.init
import com.example.cleanapp.extensions.setTintColor
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.sign_up_master.adapters.ChooserCategoryAdapter
import com.example.cleanapp.ui.sign_up_master.adapters.ChooserCityAdapter
import com.example.cleanapp.ui.sign_up_master.adapters.ChooserLanguageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpMasterFragment :
    BaseFragment<SignUpMasterFragmentBinding>(SignUpMasterFragmentBinding::inflate) {

    private val viewModel: SignUpMasterViewModel by viewModels()
    private lateinit var cityAdapter: ChooserCityAdapter
    private lateinit var categoryAdapter: ChooserCategoryAdapter
    private lateinit var languageAdapter: ChooserLanguageAdapter
    private var master = Master()

    override fun start() {
        master.user = arguments?.getParcelable("user")
        observers()
        initAdapters()
        setListeners()

        binding.btnSupplement.setTintColor(
            if (viewModel.haveSupplement)
                R.color.selection
            else
                R.color.gray_light
        )
    }

    private fun setListeners() {
        binding.editCity.setOnClickListener {
            showCityChooserDialog()
        }

        binding.editCategory.setOnClickListener {
            showCategoryChooserDialog()
        }

        binding.editLanguage.setOnClickListener {
            showLanguageChooserDialog()
        }

        binding.btnSupplement.setOnClickListener {
            viewModel.haveSupplement = !viewModel.haveSupplement
            it.setTintColor(
                if (viewModel.haveSupplement)
                    R.color.selection
                else
                    R.color.gray_light
            )
        }

        binding.btnCreateMaster.setOnClickListener {
            val city = viewModel.selectedCity
            val categories = viewModel.categories.filter { it.category?.isChecked!! }
            val languages = viewModel.languages.filter { it.second }.map { it.first }
            val haveSupplement = viewModel.haveSupplement
            master.languages = languages
            master.categories = categories
            master.ownSupplements = haveSupplement
            master.city = city
            master.user!!.isMaster = true
            if (master.city != null && master.categories != null && master.languages != null)
                viewModel.setMaster(master)
            else showErrorDialog("fill all input")
        }
    }

    private fun initAdapters() {
        cityAdapter = ChooserCityAdapter()
        viewModel.getCities()

        categoryAdapter = ChooserCategoryAdapter()
        viewModel.getCategory()

        languageAdapter = ChooserLanguageAdapter()
        viewModel.getLanguages()
    }

    private fun showCityChooserDialog() {
        val dialogBinding = ChooserDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.init(dialogBinding.root)
        cityAdapter.onClick = {
            binding.editCity.text = it.cityEn
            viewModel.selectedCity = it
            dialog.cancel()
            binding.editCity.clearFocus()
        }
        dialogBinding.rvChooser.adapter = cityAdapter
        dialogBinding.rvChooser.layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.etInput.hint = getString(R.string.choose_your_city)
        dialogBinding.btnChoose.text = getString(R.string.close)
        dialogBinding.btnChoose.setOnClickListener {
            dialog.cancel()
            binding.editCity.clearFocus()
        }
        dialog.show()
    }

    private fun showCategoryChooserDialog() {
        val dialogBinding = ChooserDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.init(dialogBinding.root)
        dialogBinding.rvChooser.adapter = categoryAdapter
        dialogBinding.rvChooser.layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.etInput.setText(getString(R.string.choose_your_categories))
        dialogBinding.etInput.isEnabled = false
        dialogBinding.btnChoose.text = getString(R.string.choose)

        dialogBinding.btnChoose.setOnClickListener {
            dialog.cancel()
            val text = viewModel.categories.filter { it.category!!.isChecked }
                .fold("") { acc, categoryMaster ->
                    "$acc, ${categoryMaster.category!!.categoryEn}"
                }.drop(1)
            binding.editCategory.text = text
        }
        dialog.show()
    }

    private fun showLanguageChooserDialog() {
        val dialogBinding = ChooserDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.init(dialogBinding.root)

        languageAdapter.onCheckLanguage = { position, data ->
            viewModel.languages[position] = data
        }

        dialogBinding.rvChooser.adapter = languageAdapter
        dialogBinding.rvChooser.layoutManager = LinearLayoutManager(requireContext())
        dialogBinding.etInput.setText(getString(R.string.choose_your_categories))
        dialogBinding.etInput.isEnabled = false
        dialogBinding.btnChoose.text = getString(R.string.choose)
        dialogBinding.btnChoose.setOnClickListener {
            dialog.cancel()
            val text = viewModel.languages.filter { it.second }.fold("") { acc, language ->
                "$acc, ${language.first}"
            }.drop(1)
            binding.editLanguage.text = text
        }
        dialog.show()
    }

    private fun observers() {
        viewModel.citiesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    cityAdapter.setItems(it.data!!)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    categoryAdapter.setItems(it.data!!)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.languagesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    languageAdapter.setItems(it.data!!)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })

        viewModel.masterCreateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    binding.progress.gone()
                    if (it.data!!) findNavController().navigate(R.id.action_signUpMasterFragment_to_homeFragment)
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })
    }

}