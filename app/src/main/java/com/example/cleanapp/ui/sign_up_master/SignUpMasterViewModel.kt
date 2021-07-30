package com.example.cleanapp.ui.sign_up_master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.City
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.user_data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpMasterViewModel @Inject constructor(
    private val userData: UserData,
    private val signUpMasterRepo: SignUpMasterRepository
) :
    ViewModel() {

    val cities = mutableListOf<City>()
    val categories = mutableListOf<MasterCategory>()
    val languages = mutableListOf<Pair<String, Boolean>>()
    var haveSupplement = false
    var selectedCity: City? = null

    private val _citiesLiveData = MutableLiveData<ResultHandler<List<City>>>()
    val citiesLiveData: LiveData<ResultHandler<List<City>>> = _citiesLiveData

    private val _categoriesLiveData = MutableLiveData<ResultHandler<List<MasterCategory>>>()
    val categoriesLiveData: LiveData<ResultHandler<List<MasterCategory>>> = _categoriesLiveData

    private val _languagesLiveData = MutableLiveData<ResultHandler<List<Pair<String, Boolean>>>>()
    val languagesLiveData: LiveData<ResultHandler<List<Pair<String, Boolean>>>> = _languagesLiveData

    private val _masterCreateLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val masterCreateLiveData: LiveData<ResultHandler<Boolean>> = _masterCreateLiveData


    fun getCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _citiesLiveData.postValue(ResultHandler.Loading(true))

                signUpMasterRepo.getCities {
                    cities.clear()
                    cities.addAll(it)
                    _citiesLiveData.postValue(ResultHandler.Success(cities))
                }
            }
        }
    }

    fun getCategory() {
        signUpMasterRepo.getCategory { list ->
            categories.clear()
            val masterCategory = list.map { MasterCategory(it, 0f) }
            categories.addAll(masterCategory)
            _categoriesLiveData.postValue(ResultHandler.Success(categories))
        }
    }

    fun getLanguages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _languagesLiveData.postValue(ResultHandler.Loading(true))

                signUpMasterRepo.getLanguages { list ->
                    languages.clear()
                    list.forEach {
                        languages.add(Pair(it, false))
                    }
                    _languagesLiveData.postValue(ResultHandler.Success(languages))
                }
            }
        }
    }

    fun setMaster(master: Master) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _masterCreateLiveData.postValue(ResultHandler.Loading(true))

                signUpMasterRepo.setMaster(master) {
                    _masterCreateLiveData.postValue(ResultHandler.Success(true))
                }
            }
        }
    }
}