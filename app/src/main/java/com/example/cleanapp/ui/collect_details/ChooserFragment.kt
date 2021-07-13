package com.example.cleanapp.ui.collect_details

import androidx.fragment.app.activityViewModels
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
    }
}