package com.example.cleanapp.ui.collect_details.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Category
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryChooserViewModel @Inject constructor(private val dbRef: DatabaseReference) :
    ViewModel() {
    private val _liveData = MutableLiveData<ResultHandler<List<Category>>>()
    val liveData: LiveData<ResultHandler<List<Category>>> = _liveData


    fun getCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRef.child("categories").get().addOnSuccessListener {
                    _liveData.postValue(ResultHandler.Success(it.getValue<List<Category>>()))
                }
            }
        }
    }
}