package com.example.cleanapp.ui.home.botoom_navigation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersRepo: OrdersRepository) : ViewModel() {

    private val _ordersLiveData = MutableLiveData<ResultHandler<List<Order>>>()
    val ordersLiveData: LiveData<ResultHandler<List<Order>>> = _ordersLiveData

    fun getOrders() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _ordersLiveData.postValue(ResultHandler.Loading(true))

                ordersRepo.getOrders {
                    _ordersLiveData.postValue(it)
                }
            }
        }
    }
}