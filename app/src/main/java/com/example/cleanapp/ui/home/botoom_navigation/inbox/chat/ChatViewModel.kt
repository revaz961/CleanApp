package com.example.cleanapp.ui.home.botoom_navigation.inbox.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Chat
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.inbox.InboxRepository
import com.example.cleanapp.user_data.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val inboxRepo: InboxRepository,
    private val userData: UserData
) : ViewModel() {
    private val _messagesLiveData = MutableLiveData<ResultHandler<List<Message>>>()
    val messagesLiveData: LiveData<ResultHandler<List<Message>>> = _messagesLiveData

    private val _singleMessagesLiveData = MutableLiveData<ResultHandler<Message>>()
    val singleMessagesLiveData: LiveData<ResultHandler<Message>> = _singleMessagesLiveData

    private val _sendMessagesLiveData = MutableLiveData<ResultHandler<String>>()
    val sendMessagesLiveData: LiveData<ResultHandler<String>> = _sendMessagesLiveData


    private val _checkAsReadLiveData = MutableLiveData<ResultHandler<Message>>()
    val checkAsReadLiveData: LiveData<ResultHandler<Message>> = _checkAsReadLiveData

    fun getCurrentUserId() = auth.currentUser!!.uid

    fun getCurrentUser() = userData.getUser()

    fun getMessages(chatId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _messagesLiveData.postValue(ResultHandler.Loading(true))

                inboxRepo.getMessages(chatId, {
                    _singleMessagesLiveData.postValue(it)
                }){
                    _messagesLiveData.postValue(it)
                }
            }
        }
    }

    fun sendMessage(message: Message, chatId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _messagesLiveData.postValue(ResultHandler.Loading(true))

                inboxRepo.sendMessage(message, chatId) {
                    _sendMessagesLiveData.postValue(it)
                }
            }
        }
    }

    fun checkAsRead(chat: Chat, message: Message) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                inboxRepo.checkReadMessage(chat, message)
            }
        }
    }
}