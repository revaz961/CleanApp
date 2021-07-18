package com.example.cleanapp.ui.login

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignInFragmentBinding
import com.example.cleanapp.extensions.isEmail
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(SignInFragmentBinding::inflate) {

    private val viewModel: SignInViewModel by viewModels()

    override fun start() {
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editEmail.text.toString()

            val errorMessage = checkUserInfo()

            if (errorMessage.isEmpty())
                viewModel.signIn(email, password)
            else
                showErrorDialog(errorMessage)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_signUpFragment)
        }
    }

    private fun checkUserInfo(): String {
        var result = ""
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if(!email.isEmail())
            result += "Invalid Email\n"

        if(password.length < 6)
            result += "Invalid Password"

        return result
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> findNavController().navigate(R.id.action_SignInFragment_to_homeFragment)
                is ResultHandler.Error -> showErrorDialog(it.message)
                is ResultHandler.Loading -> Log.d("userInfo", it.loading.toString())
            }
        })
    }

}