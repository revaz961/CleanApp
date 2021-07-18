package com.example.cleanapp.ui.home.master_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MasterResultsViewModel @Inject constructor(private val masterResultsRepo: MasterResultsRepository): ViewModel() {

    private val _exploreLiveData = MutableLiveData<ResultHandler<Master>>()
    val exploreLiveData: LiveData<ResultHandler<Master>> = _exploreLiveData

    fun getMaster(query:String){
        masterResultsRepo.getMasters(query) {
            _exploreLiveData.postValue(ResultHandler.Success(it))
        }
    }

    fun setMaster(master: Master){
        masterResultsRepo.setMasters(master){
//            getMaster()
        }
    }
}