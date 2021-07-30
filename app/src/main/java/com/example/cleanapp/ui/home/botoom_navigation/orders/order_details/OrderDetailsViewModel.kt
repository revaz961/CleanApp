package com.example.cleanapp.ui.home.botoom_navigation.orders.order_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.*
import com.example.cleanapp.user_data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val userData: UserData,
    private val ordersDetailsRepo: OrderDetailsRepository
) :
    ViewModel() {

    private val _orderDetailsLiveData = MutableLiveData<ResultHandler<Master>>()
    val ordersDetailsLiveData: LiveData<ResultHandler<Master>> = _orderDetailsLiveData

    private val _orderStatusLiveData = MutableLiveData<ResultHandler<Int>>()
    val orderStatusLiveData: LiveData<ResultHandler<Int>> = _orderStatusLiveData

    private val _reviewAddLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val reviewAddLiveData: LiveData<ResultHandler<Boolean>> = _reviewAddLiveData

    private val _isReviewAddLiveData = MutableLiveData<ResultHandler<Boolean>>()
    val isReviewAddLiveData: LiveData<ResultHandler<Boolean>> = _isReviewAddLiveData

    fun getCurrentUser() = userData.getUser()

    fun addReview(comment: Comment, stars: Int, user: User, master: Master,order:Order) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _reviewAddLiveData.postValue(ResultHandler.Loading(true))
                ordersDetailsRepo.addReview(comment, stars,master,order){
                    _reviewAddLiveData.postValue(it)
                }
            }
        }
    }

    fun isReviewAdd(masterId: String, orderId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _isReviewAddLiveData.postValue(ResultHandler.Loading(true))
                ordersDetailsRepo.isReviewAdd(masterId, orderId){
                    _isReviewAddLiveData.postValue(it)
                }
            }
        }
    }

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

                ordersDetailsRepo.cancelReservation(order) {
                    _orderStatusLiveData.postValue(it)
                }
            }
        }

    }

}