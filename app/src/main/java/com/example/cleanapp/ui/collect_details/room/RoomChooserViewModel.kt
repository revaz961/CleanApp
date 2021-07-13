package com.example.cleanapp.ui.collect_details.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.RoomCounter
import com.example.cleanapp.repository.ChooserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomChooserViewModel @Inject constructor(
    private val chooserRepository: ChooserRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<ResultHandler<Order>>()
    val liveData:LiveData<ResultHandler<Order>> = _liveData

    val roomCounters = mutableListOf<RoomCounter>(
        RoomCounter("Bedroom", 0),
        RoomCounter("Kitchen", 0),
        RoomCounter("Living Room", 0),
        RoomCounter("Entrance", 0),
        RoomCounter("Additional Rooms", 0),
        RoomCounter("Balcony", 0),
        RoomCounter("Garden", 0)
    )

    fun setOrderInDb(order: Order) {
        chooserRepository.setOrderInDb(order){isValid,errorMessage ->
            if (isValid) {
                _liveData.postValue(ResultHandler.Success(order))
            } else {
                _liveData.postValue(ResultHandler.Error(order, errorMessage))
            }
        }
    }

}