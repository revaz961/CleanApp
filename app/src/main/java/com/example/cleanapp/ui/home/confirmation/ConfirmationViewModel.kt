package com.example.cleanapp.ui.home.confirmation

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Card
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val confirmationRepo: ConfirmationRepository,
    private val auth: FirebaseAuth
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

    fun getUserId() = auth.currentUser?.uid

    fun confirmOrder(order: Order) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                confirmationRepo.confirmOrder(order) {
                    _confirmationLiveData.postValue(it)
                }
            }
        }
    }

    fun getCards() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
//                delay(1000)
                confirmationRepo.getCards {
                    if (it is ResultHandler.Success) {
                        cards.clear()
                        cards.addAll(it.data!!)
                        d("cardsLog","in viewmodel size:${cards.size}")
                    }
                    _cardsLiveData.postValue(it)
                }
            }
        }
    }

    fun addCard(card: Card) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                confirmationRepo.addCard(card) {
                    if (it is ResultHandler.Success)
                        cards.add(card)

                    _cardAddLiveData.postValue(it)
                }
            }
        }
    }

    fun sendMessage(message: String) {
//        confirmationRepo.sendMessage(message) {
//            _messageSendLiveData.postValue(ResultHandler.Success(it))
//        }
    }
}