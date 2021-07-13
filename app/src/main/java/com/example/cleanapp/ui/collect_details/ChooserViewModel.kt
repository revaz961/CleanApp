package com.example.cleanapp.ui.collect_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class ChooserViewModel @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val dbRef: DatabaseReference
) : ViewModel() {



    private var _fragmentTitleLiveData = MutableLiveData<String>()
    val fragmentTitleLiveData: LiveData<String> = _fragmentTitleLiveData

    fun setFragmentTitle(str: String) {
        _fragmentTitleLiveData.postValue(str)
    }





//    private val order: Order by lazy {
//        Order()
//    }

//    private val _liveData = MutableLiveData<ResultHandler<Order>>()
//    val LiveData: LiveData<ResultHandler<Order>> = _liveData
//
//    fun setCity(city: City) {
//        order.cityId = city
//    }

//    fun setCategory(category: Category) {
//        order.categoryId = category
//    }
//
//    fun setDate(date: Long) {
//        order.date = date
//    }
//
//    fun setRoomCount(roomCounter: List<RoomCounter>) {
//        order.roomCount = roomCounter
//    }
//
//    fun setOrderInDb() {
//        dbRef.child("users")
//            .child(auth.currentUser!!.uid)
//            .child("order")
//            .setValue(order){ error, ref ->
//                val message = error?.message ?: ""
//                if (error != null) {
//                    _liveData.postValue(ResultHandler.Success(order))
//                } else {
//                    _liveData.postValue(ResultHandler.Error(order, message))
//                }
//            }
//    }
//
//    fun getOrderFromDb() {
//        dbRef.child("users")
//            .child(auth.currentUser!!.uid)
//            .child("order")
//            .get().addOnSuccessListener {
//                val res = it.getValue<Order>()
//                _liveData.postValue(ResultHandler.Success(res))
//            }
//    }

}