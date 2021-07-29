package com.example.cleanapp.ui.home.botoom_navigation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import com.example.cleanapp.repository.UserRepository
import com.example.cleanapp.user_data.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepo: UserRepository,
    private val userData: UserData
) : ViewModel() {
    fun signOut() {
        userData.deleteUser()
        auth.signOut()
    }

    private val _userLiveData = MutableLiveData<ResultHandler<User>>()
    val userLiveData: LiveData<ResultHandler<User>> = _userLiveData

    private var user : User? = null

    fun changePersonalInfo() {
        userRepo.getUser {

        }
    }

    fun getUser() {
        userRepo.getUser {
            if (it is ResultHandler.Success)
                user = it.data
            _userLiveData.postValue(it)
        }
    }
}