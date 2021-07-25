package com.example.cleanapp.ui.home.botoom_navigation.profile

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val profileViewModel:ProfileViewModel by viewModels()

    override fun start() {
        binding.signOut.setOnClickListener {
            profileViewModel.signOut()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_SignInFragment)
        }
    }

}