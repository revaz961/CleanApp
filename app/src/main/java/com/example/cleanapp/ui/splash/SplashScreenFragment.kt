package com.example.cleanapp.ui.splash

import android.animation.ValueAnimator
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SplashScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<SplashScreenFragmentBinding>(SplashScreenFragmentBinding::inflate) {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun start() {
//        initAnim()
        observes()
        viewModel.checkAuth()

    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            if (it)
                findNavController().navigate(R.id.action_splashScreenFragment_to_chooserFragment2)
            else
                findNavController().navigate(R.id.action_splashScreenFragment_to_chooserFragment2)
        })
    }
}