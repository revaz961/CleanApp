package com.example.cleanapp.ui.collect_details.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooserDateViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) : ViewModel() {
    private val _dateLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val dateLiveData: LiveData<ResultHandler<Boolean>> = _dateLiveData
}