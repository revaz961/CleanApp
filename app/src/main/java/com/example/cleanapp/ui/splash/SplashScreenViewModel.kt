package com.example.cleanapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {
    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> = _liveData

    fun checkAuth() {
        if (auth.currentUser == null)
            _liveData.postValue(false)
        else
            _liveData.postValue(true)
    }
}