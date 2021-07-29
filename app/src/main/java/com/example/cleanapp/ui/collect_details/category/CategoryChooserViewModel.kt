package com.example.cleanapp.ui.collect_details.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Category
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryChooserViewModel @Inject constructor(private val chooserRepo: ChooserRepository) :
    ViewModel() {
    private val _categoryLiveData = MutableLiveData<ResultHandler<List<Category>>>()
    val categoryLiveData: LiveData<ResultHandler<List<Category>>> = _categoryLiveData


    fun getCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _categoryLiveData.postValue(ResultHandler.Loading(true))

                chooserRepo.getCategory {
                    _categoryLiveData.postValue(it)
                }
            }
        }
    }
}