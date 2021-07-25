package com.example.cleanapp.ui.sign_up_master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpMasterViewModel @Inject constructor(private val signUpMasterRepo: SignUpMasterRepository) :
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
        signUpMasterRepo.getCities {
            cities.clear()
            cities.addAll(it)
            _citiesLiveData.postValue(ResultHandler.Success(cities))
        }
    }

    fun getCategory() {
        signUpMasterRepo.getCategory { list ->
            categories.clear()
            val masterCategory = list.map { MasterCategory(it,0f) }
            categories.addAll(masterCategory)
            _categoriesLiveData.postValue(ResultHandler.Success(categories))
        }
    }

    fun getLanguages() {
        signUpMasterRepo.getLanguages { list ->
            languages.clear()
            list.forEach {
                languages.add(Pair(it, false))
            }
            _languagesLiveData.postValue(ResultHandler.Success(languages))
        }
    }

    fun setMaster(master:Master){
        signUpMasterRepo.setMaster(master){
            _masterCreateLiveData.postValue(ResultHandler.Success(true))
        }
    }
}