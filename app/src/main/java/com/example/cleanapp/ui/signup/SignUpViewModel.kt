package com.example.cleanapp.ui.signup

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) : ViewModel() {

    private val _liveData = MutableLiveData<ResultHandler<FirebaseUser>>()
    val liveData: LiveData<ResultHandler<FirebaseUser>> = _liveData

    fun signUp(email: String, password: String, activity: Activity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _liveData.postValue(ResultHandler.Loading(true))
                register(email, password, activity)
            }
        }
    }

    private fun register(email: String, password: String, activity: Activity) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _liveData.postValue(ResultHandler.Success(user))
                } else {
                    _liveData.postValue(ResultHandler.Error(null, task.exception!!.message!!))
                }
            }
    }

    fun setUserProfile(profile: UserProfile) {
        dbRef.child("users").child("${auth.currentUser!!.uid}").setValue(profile)
    }
}