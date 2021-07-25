package com.example.cleanapp.ui.signup

import android.util.Log.d
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignUpFragmentBinding
import com.example.cleanapp.extensions.isEmail
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
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

            val errorMessage = checkUserInfo()

            if (errorMessage.isEmpty())
                viewModel.signUp(email, password)
            else
                showErrorDialog(errorMessage)
        }

        binding.btnCreateMaster.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editEmail.text.toString().trim()
            val errorMessage = checkUserInfo()

            if (errorMessage.isEmpty())
                viewModel.registerAsMaster(email, password)
            else
                showErrorDialog(errorMessage)
        }
    }

    private fun checkUserInfo(): String {
        val email = binding.editEmail.text.toString().trim()
        val name = binding.editName.text.toString().trim()
        val surname = binding.editSurname.text.toString().trim()
        val password = binding.editEmail.text.toString().trim()
        val repeatPassword = binding.editEmail.text.toString().trim()
        val phoneNumber = binding.editPhone.text.toString().trim()

        var result = ""

        if (name.length < 3)
            result += "Invalid Name\n"

        if (surname.length < 3)
            result += "Invalid Surname\n"

        if (!email.isEmail())
            result += "Invalid Email\n"

        if (password.length < 6)
            result += "Invalid Password\n"

        if (repeatPassword != password)
            result += "Invalid Repeated Password"

        if (phoneNumber.length != 9)
            result += "Invalid number\n"



        return result
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    viewModel.setUserProfile(User(
                        it.data!!.uid,
                        it.data.email!!,
                        binding.editName.text.toString().trim(),
                        binding.editSurname.text.toString().trim(),
                        binding.editPhone.text.toString()
                    ))
                    findNavController().navigate(R.id.action_global_homeFragment)
                }

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> d("userInfo", it.loading.toString())
            }
        })

        viewModel.masterLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    val user = User(
                        it.data!!.uid,
                        it.data.email!!,
                        binding.editName.text.toString().trim(),
                        binding.editSurname.text.toString().trim(),
                        binding.editPhone.text.toString()
                    )
                    viewModel.setUserProfile(user)
                    findNavController().navigate(R.id.action_signUpFragment_to_signUpMasterFragment,
                        bundleOf("user" to user))
                }

                is ResultHandler.Error -> showErrorDialog(it.message)

                is ResultHandler.Loading -> d("userInfo", it.loading.toString())
            }
        })
    }
}