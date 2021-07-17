package com.example.cleanapp.ui.collect_details.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.City
import com.example.cleanapp.models.ResultHandler
import com.google.android.gms.location.LocationRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityChooserViewModel @Inject constructor(private val dbRef: DatabaseReference) : ViewModel() {
    private val _citiesLiveData = MutableLiveData<ResultHandler<List<City>>>()
    val citiesLiveData: LiveData<ResultHandler<List<City>>> = _citiesLiveData


    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRef.child("cities").addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        _citiesLiveData.postValue(ResultHandler.Success(snapshot.getValue<List<City>>()))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _citiesLiveData.postValue(ResultHandler.Error(null,error.message))
                    }

                })
            }
        }
    }
}