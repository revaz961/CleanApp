package com.example.cleanapp.ui.home.master_reserve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MasterReserveViewModel @Inject constructor(private val masterReserveRepo: MasterReserveRepository) :
    ViewModel() {
    private val _chatLiveData = MutableLiveData<ResultHandler<Chat>>()
    val chatLiveData: LiveData<ResultHandler<Chat>> = _chatLiveData

    fun startChat(master: Master) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _chatLiveData.postValue(ResultHandler.Loading(true))
                masterReserveRepo.startChat(master) {
                    _chatLiveData.postValue(it)
                }
            }
        }
    }

    fun reportMaster(master: Master) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                //todo report master
            }
        }
    }
}