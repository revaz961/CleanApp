package com.example.cleanapp.ui.login

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignInFragmentBinding
import com.example.cleanapp.extension.isEmail
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private val viewModel: SignInViewModel by viewModels()

    override fun start() {
        init()
    }

    private fun init() {
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editEmail.text.toString()
            if (checkUserInfo())
                viewModel.signIn(email, password, requireActivity())
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_signUpFragment)
        }
    }

    private fun checkUserInfo(): Boolean {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editEmail.text.toString().trim()
        return email.isEmail() && password.trim().length > 6
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> findNavController().navigate(R.id.action_SignInFragment_to_chooserFragment)
                is ResultHandler.Error -> Log.d("userInfo", it.message)
                is ResultHandler.Loading -> Log.d("userInfo", it.loading.toString())
            }
        })
    }

}