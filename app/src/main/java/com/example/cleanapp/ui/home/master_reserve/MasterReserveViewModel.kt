package com.example.cleanapp.ui.home.master_reserve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MasterReserveViewModel : ViewModel() {
    private val _chatLiveData = MutableLiveData<ResultHandler<Chat>>()
    val chatLiveData: LiveData<ResultHandler<Chat>> = _chatLiveData

    fun startChat(master: Master) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _chatLiveData.postValue(ResultHandler.Loading(true))


            }
        }
    }
}