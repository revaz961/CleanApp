package com.example.cleanapp.ui.home.botoom_navigation.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val auth:FirebaseAuth): ViewModel() {
    fun signOut(){
        auth.signOut()
    }
}