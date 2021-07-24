package com.example.cleanapp.ui.sign_up_master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Category
import com.example.cleanapp.models.City
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.ResultHandler
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

    private val _categoriesLiveData = MutableLiveData<ResultHandler<List<Category>>>()
    val categoriesLiveData: LiveData<ResultHandler<List<Category>>> = _categoriesLiveData

    private val _languagesLiveData = MutableLiveData<ResultHandler<List<Pair<String, Boolean>>>>()
    val languagesLiveData: LiveData<ResultHandler<List<Pair<String, Boolean>>>> = _languagesLiveData

    fun getCities() {
        signUpMasterRepo.getCities {
            cities.clear()
            cities.addAll(it)
            _citiesLiveData.postValue(ResultHandler.Success(cities))
        }
    }

    fun getCategory() {
        signUpMasterRepo.getCategory {
            categories.clear()
            categories.addAll(it)
            _categoriesLiveData.postValue(ResultHandler.Success(categories))
        }
    }

    fun getLanguages() {
        signUpMasterRepo.getLanguages {
            languages.clear()
            it.forEach {
                languages.add(Pair(it, false))
            }
            _languagesLiveData.postValue(ResultHandler.Success(languages))
        }
    }
}