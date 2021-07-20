package com.example.cleanapp.ui.signup

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignUpFragmentBinding
import com.example.cleanapp.extensions.isEmail
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.UserProfile
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun start() {
        init()
    }

    private fun init() {
        observes()
        setListeners()
    }

    private fun setListeners() {
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_SignInFragment)
        }

        binding.btnCreateUser.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editEmail.text.toString().trim()
            val name = binding.editName.text.toString().trim()


            val errorMessage = checkUserInfo()
            if (errorMessage.isEmpty())
                viewModel.signUp(email, password)
            else
                showErrorDialog(errorMessage)
        }
    }

    private fun checkUserInfo(): String {
        val email = binding.editEmail.text.toString().trim()
        val name = binding.editName.text.toString().trim()
        val password = binding.editEmail.text.toString().trim()
        val repeatPassword = binding.editEmail.text.toString().trim()

        var result = ""

        if (!email.isEmail())
            result += "Invalid Email\n"

        if (name.length < 3)
            result += "Invalid Name\n"

        if (password.length < 6)
            result += "Invalid Password\n"

        if (repeatPassword != password)
            result += "Invalid Repeated Password"

        return result
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    viewModel.setUserProfile(UserProfile(
                        it.data!!.uid,
                        it.data.email!!,
                        binding.editName.text.toString().trim(),
                        binding.editPhone.text.toString()
                    ))
                    findNavController().navigate(R.id.action_global_homeFragment)
                }

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> d("userInfo", it.loading.toString())
            }
        })
    }
}