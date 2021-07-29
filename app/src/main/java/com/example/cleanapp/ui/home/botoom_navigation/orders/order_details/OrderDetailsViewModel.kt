package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val ordersDetailsRepo: OrderDetailsRepository) :
    ViewModel() {

    private val _orderDetailsLiveData = MutableLiveData<ResultHandler<Master>>()
    val ordersDetailsLiveData: LiveData<ResultHandler<Master>> = _orderDetailsLiveData

    private val _orderStatusLiveData = MutableLiveData<ResultHandler<Int>>()
    val orderStatusLiveData: LiveData<ResultHandler<Int>> = _orderStatusLiveData

    fun getMaster(uid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _orderDetailsLiveData.postValue(ResultHandler.Loading(true))

                ordersDetailsRepo.getMaster(uid) {
                    _orderDetailsLiveData.postValue(it)
                }
            }
        }
    }

    fun cancelReservation(order: Order) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _orderDetailsLiveData.postValue(ResultHandler.Loading(true))

                ordersDetailsRepo.cancelReservation(order){
                    _orderStatusLiveData.postValue(it)
                }
            }
        }

    }

}