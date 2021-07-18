package com.example.cleanapp.ui.home

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    override fun start() {
        initBottom()
    }

    private fun initBottom(){
        val navController =
            childFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navController.navController)
    }
}