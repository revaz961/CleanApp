package com.example.cleanapp.ui.collect_details.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.RoomCounter
import com.example.cleanapp.ui.collect_details.ChooserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomChooserViewModel @Inject constructor(
    private val chooserRepo: ChooserRepository
) : ViewModel() {

    val roomCounters = mutableListOf<RoomCounter>()

    private val _roomLiveData = MutableLiveData<ResultHandler<List<RoomCounter>>>()
    val roomLiveData: LiveData<ResultHandler<List<RoomCounter>>> = _roomLiveData

    fun getRooms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _roomLiveData.postValue(ResultHandler.Loading(true))

                chooserRepo.getRooms {
                    if (it is ResultHandler.Success) {
                        roomCounters.clear()
                        roomCounters.addAll(it.data!!)
                    }
                    _roomLiveData.postValue(it)
                }

            }
        }
    }

}