package com.example.cleanapp.ui.collect_details.category

import android.graphics.Bitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CategoryChooserFragmentBinding
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryChooserFragment :
    BaseFragment<CategoryChooserFragmentBinding>(CategoryChooserFragmentBinding::inflate) {

    private val viewModel: CategoryChooserViewModel by viewModels()
    private val shareViewModel: ChooserViewModel by activityViewModels()
    private lateinit var adapter: CategoryAdapter

    override fun start() {
        initRecycler()
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
//            requireActivity().findNavController(R.id.nav_host_fragment).navigateUp()
//            shareViewModel.setFragmentTitle(getString(R.string.category_title))


        }
    }

    private fun initRecycler() {
        adapter = CategoryAdapter().apply {
            chooseCategory = {

                val order = Order()
                order.category = it
                shareViewModel.setFragmentTitle(getString(R.string.date_title))
                findNavController().navigate(
                    R.id.action_categoryChooserFragment_to_chooserDateFragment,
                    bundleOf("order" to order)
                )

            }
        }
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCategory()

    }

    private fun observes() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> adapter.setItems(it.data!!)

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> {
                }
            }
        })
    }
}
