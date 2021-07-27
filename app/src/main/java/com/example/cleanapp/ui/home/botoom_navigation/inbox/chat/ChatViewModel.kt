package com.example.cleanapp.ui.home.botoom_navigation.inbox.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.inbox.InboxRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val inboxRepo: InboxRepository
) : ViewModel() {
    private val _messagesLiveData = MutableLiveData<ResultHandler<List<Message>>>()
    val messagesLiveData: LiveData<ResultHandler<List<Message>>> = _messagesLiveData

    fun getCurrentUserId() = auth.currentUser!!.uid

    fun getMessages(chatId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                inboxRepo.getMessages(chatId) {
                    _messagesLiveData.postValue(it)
                }
            }
        }
    }
}