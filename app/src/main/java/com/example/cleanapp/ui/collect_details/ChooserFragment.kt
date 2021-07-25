package com.example.cleanapp.ui.collect_details

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ChooserFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooserFragment : BaseFragment<ChooserFragmentBinding>(ChooserFragmentBinding::inflate) {

    private val viewModel: ChooserViewModel by activityViewModels()
    override fun start() {
        observes()
    }

    private fun observes() {
        viewModel.fragmentTitleLiveData.observe(viewLifecycleOwner, {
            binding.chooserInstructions.text = it
        })
        viewModel.navigateLiveData.observe(viewLifecycleOwner, {
            findNavController().setGraph(
                R.navigation.master_reservation,
                bundleOf("order" to it.second)
            )
        })
    }
}