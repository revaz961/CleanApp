package com.example.cleanapp.ui.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.SignUpFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.extensions.isEmail
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(SignUpFragmentBinding::inflate) {
    companion object {
        const val REQUEST_CODE = 1
    }

    private val viewModel: SignUpViewModel by viewModels()
    private var uri: Uri? = null

    override fun start() {
        init()
    }

    private fun init() {
        observes()
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            uri = data?.data!!
            binding.btnImage.setImageURI(uri)
        }
    }

    private fun setListeners() {

        binding.btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }

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
        val termAndCondition = binding.checkAgree.isChecked

        var result = ""

        if (uri == null)
            result += "Add Image\n"

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

        if (!termAndCondition)
            result += "Agree Term And Condition\n"


        return result
    }

    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    viewModel.setUserProfile(
                        User(
                            it.data!!.uid,
                            it.data.email!!,
                            binding.editName.text.toString().trim(),
                            binding.editSurname.text.toString().trim(),
                            binding.editPhone.text.toString(),
                            it.data.uid,
                            false,
                            Calendar.getInstance().timeInMillis
                        )
                    )
                    viewModel.uploadImage(uri!!)
                    findNavController().navigate(R.id.action_global_homeFragment)
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> binding.progress.goneIf(!it.loading)
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
                        binding.editPhone.text.toString(),
                        it.data.uid,
                        false,
                        Calendar.getInstance().timeInMillis
                    )

                    viewModel.setUserProfile(user)
                    viewModel.uploadImage(uri!!)

                    findNavController().navigate(
                        R.id.action_signUpFragment_to_signUpMasterFragment,
                        bundleOf("user" to user)
                    )
                    binding.progress.gone()
                }

                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> binding.progress.goneIf(!it.loading)
            }
        })
    }
}