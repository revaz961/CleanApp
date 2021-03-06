package com.example.cleanapp.ui.home.master_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MasterResultsViewModel @Inject constructor(private val masterResultsRepo: MasterResultsRepository) :
    ViewModel() {

    val masters = mutableListOf<Master>()

    private val _exploreLiveData = MutableLiveData<ResultHandler<List<Master>>>()
    val exploreLiveData: LiveData<ResultHandler<List<Master>>> = _exploreLiveData

    fun getMaster(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _exploreLiveData.postValue(ResultHandler.Loading(true))

                masterResultsRepo.getMasters(query) {
                    if (it is ResultHandler.Success) {
                        masters.clear()
                        masters.addAll(it.data!!)
                    }
                    _exploreLiveData.postValue(it)
                }
            }
        }
    }

}