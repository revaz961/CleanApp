package com.example.cleanapp.ui.splash

import android.animation.Animator
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
        observes()
        viewModel.checkAuth()
    }

    private fun goTo(isValiD:Boolean) {
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                if(isValiD)
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                else
                    findNavController().navigate(R.id.action_splashScreenFragment_to_SignInFragment)
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}

        })
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            goTo(it)
        })
    }

}