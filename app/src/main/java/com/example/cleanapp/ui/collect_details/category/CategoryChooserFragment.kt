package com.example.cleanapp.ui.collect_details.category


import android.util.Log.d
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CategoryChooserFragmentBinding
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
    }

    private fun initRecycler() {
        adapter = CategoryAdapter().apply {
            chooseCategory = {
                shareViewModel.setCategory(it)
                findNavController().navigate(R.id.action_categoryChooserFragment_to_chooserDateFragment2)
                shareViewModel.setFragmentId(it.category_en)
                d("FRAGID", "${shareViewModel.fragmentIdLiveData.value.toString()}")
            }
        }
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCategory()

    }

    private fun observes() {

        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    adapter.setItems(it.data!!)
                }
            }
        })
    }



//    private fun initRecycler() {
//        adapter = RoomCounterAdapter().apply {
//            setItems(viewModel.roomCounters)
//
//            increaseClick = { position ->
//                if (viewModel.roomCounters[position].count < 10)
//                    ++viewModel.roomCounters[position].count
//                else
//                    viewModel.roomCounters[position].count
//            }
//
//            decreaseClick = { position ->
//                if (viewModel.roomCounters[position].count > 0)
//                    --viewModel.roomCounters[position].count
//                else
//                    0
//            }
//        }

//        binding.rvRoom.adapter = adapter
//        binding.rvRoom.layoutManager = LinearLayoutManager(requireContext())
//    }

}