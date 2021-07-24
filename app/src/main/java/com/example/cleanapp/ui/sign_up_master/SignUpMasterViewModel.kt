package com.example.cleanapp.ui.sign_up_master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.City
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpMasterViewModel @Inject constructor(private val signUpMasterRepo: SignUpMasterRepository) :
    ViewModel() {
    private val _citiesLiveData = MutableLiveData<ResultHandler<List<City>>>()
    val citiesLiveData: LiveData<ResultHandler<List<City>>> = _citiesLiveData

        fun getCities(){
            signUpMasterRepo.getCities {
                _citiesLiveData.postValue(ResultHandler.Success(it))
            }
        }
}