package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.home.botoom_navigation.orders.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val ordersDetailsRepo: OrderDetailsRepository) :
    ViewModel() {

    private val _orderDetailsLiveData = MutableLiveData<ResultHandler<Master>>()
    val ordersDetailsLiveData: LiveData<ResultHandler<Master>> = _orderDetailsLiveData

    fun getMaster(uid: String) {
        ordersDetailsRepo.getMaster(uid) {
            _orderDetailsLiveData.postValue(it)
        }
    }

    fun cancelReservation(order: Order) {
        ordersDetailsRepo.cancelReservation(order)
    }

}