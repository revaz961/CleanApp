package com.example.cleanapp.ui.home.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Card
import com.example.cleanapp.models.Message
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(private val confirmationRepo: ConfirmationRepository): ViewModel() {

    private val _confirmationLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val confirmationLiveData: LiveData<ResultHandler<Boolean>> = _confirmationLiveData

    private val _cardsLiveData = MutableLiveData<ResultHandler<List<Card>>>()
    val cardsLiveData: LiveData<ResultHandler<List<Card>>> = _cardsLiveData

    private val _cardAddLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val cardAddLiveData: LiveData<ResultHandler<Boolean>> = _cardAddLiveData

    private val _messageSendLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val messageSendLiveData: LiveData<ResultHandler<Boolean>> = _messageSendLiveData

    var currentCard = Card()

    fun confirmOrder(order: Order){
        confirmationRepo.confirmOrder(order) {
            _confirmationLiveData.postValue(ResultHandler.Success(it))
        }
    }

    fun getCards(){
        confirmationRepo.getCards {
            _cardsLiveData.postValue(ResultHandler.Success(it))
        }
    }

    fun addCard(card: Card){
        confirmationRepo.addCard(card) {
            _cardAddLiveData.postValue(ResultHandler.Success(it))
        }
    }

    fun sendMessage(message: String){
//        confirmationRepo.sendMessage(message) {
//            _messageSendLiveData.postValue(ResultHandler.Success(it))
//        }
    }
}