package com.example.cleanapp.ui.home.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.*
import com.example.cleanapp.user_data.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val confirmationRepo: ConfirmationRepository,
    private val auth: FirebaseAuth,
    private val userData: UserData
) : ViewModel() {

    private val _confirmationLiveData = MutableLiveData<ResultHandler<Order>>()
    val confirmationLiveData: LiveData<ResultHandler<Order>> = _confirmationLiveData

    private val _cardsLiveData = MutableLiveData<ResultHandler<List<Card>>>()
    val cardsLiveData: LiveData<ResultHandler<List<Card>>> = _cardsLiveData

    private val _cardAddLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val cardAddLiveData: LiveData<ResultHandler<Boolean>> = _cardAddLiveData

    private val _messageSendLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val messageSendLiveData: LiveData<ResultHandler<Boolean>> = _messageSendLiveData

    var currentCard = Card()

    var cards = mutableListOf<Card>()

    fun getCurrentUser() = userData.getUser()

    fun getUserId() = auth.currentUser?.uid!!

    fun confirmOrder(order: Order) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _confirmationLiveData.postValue(ResultHandler.Loading(true))

                confirmationRepo.confirmOrder(order) {
                    _confirmationLiveData.postValue(it)
                }
            }
        }
    }

    fun getCards() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _cardsLiveData.postValue(ResultHandler.Loading(true))

                confirmationRepo.getCards {
                    if (it is ResultHandler.Success) {
                        cards.clear()
                        cards.addAll(it.data!!)
                    }
                    _cardsLiveData.postValue(it)
                }
            }
        }
    }

    fun addCard(card: Card) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _cardAddLiveData.postValue(ResultHandler.Loading(true))

                confirmationRepo.addCard(card) {
                    if (it is ResultHandler.Success)
                        cards.add(card)

                    _cardAddLiveData.postValue(it)
                }
            }
        }
    }

    fun sendMessage(message: Message, chat: Chat, user: User, master: Master) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _messageSendLiveData.postValue(ResultHandler.Loading(true))

                confirmationRepo.sendMessage(message, chat, user, master) {
                    _messageSendLiveData.postValue(it)
                }
            }
        }
    }
}