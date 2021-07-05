package com.example.cleanapp.ui.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private val _liveData = MutableLiveData<ResultHandler<FirebaseUser>>()
    val liveData: LiveData<ResultHandler<FirebaseUser>> = _liveData

    fun signIn(email: String, password: String, activity: Activity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _liveData.postValue(ResultHandler.Loading(true))
                logIn(email, password, activity)
            }
        }
    }

    suspend fun logIn(email: String, password: String, activity: Activity) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _liveData.postValue(ResultHandler.Success(user))
                } else {
                    _liveData.postValue(ResultHandler.Error(null, task.exception!!.message!!))
                }
            }
    }
}