package com.example.cleanapp.ui.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import com.example.cleanapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<ResultHandler<FirebaseUser>>()
    val liveData: LiveData<ResultHandler<FirebaseUser>> = _liveData

    private val _masterLiveData = MutableLiveData<ResultHandler<FirebaseUser>>()
    val masterLiveData: LiveData<ResultHandler<FirebaseUser>> = _masterLiveData

    fun signUp(email: String, password: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                _liveData.postValue(ResultHandler.Loading(true))

                register(email, password)
            }
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _liveData.postValue(ResultHandler.Loading(true))

                authRepository.register(email, password) { user, errorMessage ->
                    if (user != null)
                        _liveData.postValue(ResultHandler.Success(user))
                    else
                        _liveData.postValue(ResultHandler.Error(null, errorMessage))
                }
            }
        }
    }

    fun registerAsMaster(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _masterLiveData.postValue(ResultHandler.Loading(true))

                authRepository.register(email, password) { user, errorMessage ->
                    if (user != null)
                        _masterLiveData.postValue(ResultHandler.Success(user))
                    else
                        _masterLiveData.postValue(ResultHandler.Error(null, errorMessage))
                }
            }

        }
    }

    fun setUserProfile(profile: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.setUserProfile(profile)
            }
        }
    }


    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.uploadImage(uri)
            }
        }
    }
}
