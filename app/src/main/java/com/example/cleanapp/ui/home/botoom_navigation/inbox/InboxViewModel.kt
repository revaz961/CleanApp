package com.example.cleanapp.ui.home.botoom_navigation.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.user_data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val inboxRepo: InboxRepository,
    private val userData: UserData
) : ViewModel() {

    private val _chatsLiveData = MutableLiveData<ResultHandler<List<Chat>>>()
    val chatsLiveData: LiveData<ResultHandler<List<Chat>>> = _chatsLiveData

    private val _checkChatLiveData = MutableLiveData<ResultHandler<Chat>>()
    val checkChatLiveData: LiveData<ResultHandler<Chat>> = _checkChatLiveData

    fun getCurrentUser() = userData.getUser()!!

    fun getChats() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _chatsLiveData.postValue(ResultHandler.Loading(true))

                inboxRepo.getChats {
                    _chatsLiveData.postValue(it)
                }
            }
        }
    }


    fun checkReadMessage(chat: Chat) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _checkChatLiveData.postValue(ResultHandler.Loading(true))

                inboxRepo.checkReadChat(chat) {
                    _checkChatLiveData.postValue(it)
                }
            }
        }
    }

}