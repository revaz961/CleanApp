package com.example.cleanapp.ui.home.master_results

import android.net.Uri
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
                masterResultsRepo.getMasters(query) {
                    masters.clear()
                    masters.addAll(it)
                    _exploreLiveData.postValue(ResultHandler.Success(masters))
                }
            }
        }
    }

    fun getImage(path: String, uri: Uri) {

    }

    fun setMaster(master: Master) {
        masterResultsRepo.setMasters(master) {
//            getMaster()
        }
    }
}