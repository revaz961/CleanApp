package com.example.cleanapp.ui.home.botoom_navigation.profile

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.ProfileFragmentBinding
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {

    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var user: User

    override fun start() {
        init()
        observes()
        setListeners()
        binding.tvSignOut.setOnClickListener {
            profileViewModel.signOut()
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_global_SignInFragment)
        }
    }

    private fun init() {
        profileViewModel.getUser()
    }

    private fun setListeners() {
        binding.tvSwitchToMaster.setOnClickListener {
            val user = profileViewModel.getCurrentUser()
            if (!user.isMaster!!)
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(
                    R.id.action_global_signUpMasterFragment,
                    bundleOf("user" to user)
                )
        }
    }

    private fun observes() {
        profileViewModel.userLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    user = it.data!!
                }
                is ResultHandler.Error -> showErrorDialog(it.message)
                is ResultHandler.Loading -> {
                }
            }
        })
    }
}