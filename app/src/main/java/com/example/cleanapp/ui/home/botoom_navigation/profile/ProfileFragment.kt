package com.example.cleanapp.ui.home.botoom_navigation.profile

import androidx.fragment.app.viewModels
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val profileViewModel:ProfileViewModel by viewModels()

    override fun start() {
        binding.signOut.setOnClickListener {
            profileViewModel.signOut()
        }
    }

}