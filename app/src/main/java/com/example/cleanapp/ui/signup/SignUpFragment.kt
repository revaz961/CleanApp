package com.example.cleanapp.ui.signup

import android.util.Log.d
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignUpFragmentBinding
import com.example.cleanapp.extension.isEmail
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

        binding.btnSignUp.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editEmail.text.toString().trim()
            if (checkUserInfo())
                viewModel.signUp(email, password, requireActivity())
        }
    }

    private fun checkUserInfo(): Boolean {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editEmail.text.toString().trim()
        val repeatPassword = binding.editEmail.text.toString().trim()
        return email.isEmail() && password.length > 6 && password == repeatPassword
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    viewModel.setUserProfile(UserProfile(
                        it.data!!.uid,
                        it.data.email!!,
                        binding.editFullName.text.toString().trim(),
                        binding.editAge.text.toString().trim().toInt(),
                        binding.editPhone.text.toString(),
                        (binding.rgGender.children.find { it is RadioButton && it.isChecked } as RadioButton).text.toString()
                    ))
                }
                is ResultHandler.Error -> d("userInfo", it.message)
                is ResultHandler.Loading -> d("userInfo", it.loading.toString())
            }
        })
    }
}