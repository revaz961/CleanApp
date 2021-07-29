package com.example.cleanapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.repository.AuthRepository
import com.example.cleanapp.user_data.UserData
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<ResultHandler<FirebaseUser>>()
    val liveData: LiveData<ResultHandler<FirebaseUser>> = _liveData

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _liveData.postValue(ResultHandler.Loading(true))

                logIn(email,password)
            }
        }
    }

    private fun logIn(email: String, password: String){

        authRepository.logIn(email, password) { user, errorMessage ->

            if (user != null)
                _liveData.postValue(ResultHandler.Success(user))
            else
                _liveData.postValue(ResultHandler.Error(null, errorMessage))

        }
    }
}