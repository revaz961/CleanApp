package com.example.cleanapp.ui.home.botoom_navigation.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.master_results.MasterResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(private val inboxRepo: InboxRepository): ViewModel() {

    private val _chatsLiveData = MutableLiveData<ResultHandler<List<Chat>>>()
    val chatsLiveData: LiveData<ResultHandler<List<Chat>>> = _chatsLiveData

    fun getChats(){
        inboxRepo.getChats {
            _chatsLiveData.postValue(ResultHandler.Success(it))
        }
    }

}