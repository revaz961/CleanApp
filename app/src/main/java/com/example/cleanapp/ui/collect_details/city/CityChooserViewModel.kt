package com.example.cleanapp.ui.collect_details.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.City
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CityChooserViewModel @Inject constructor(private val chooserRepo: ChooserRepository) :
    ViewModel() {
    private val _citiesLiveData = MutableLiveData<ResultHandler<List<City>>>()
    val citiesLiveData: LiveData<ResultHandler<List<City>>> = _citiesLiveData


    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                chooserRepo.getCities {
                    _citiesLiveData.postValue(it)
                }

            }
        }
    }
}