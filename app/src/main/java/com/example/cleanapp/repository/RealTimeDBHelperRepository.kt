package com.example.cleanapp.repository

import com.example.cleanapp.models.ResultHandler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class RealTimeDBHelperRepository @Inject constructor(private val dbRef: DatabaseReference) {

    fun getAllChildSnapshot(path: String, action: (ResultHandler<DataSnapshot>) -> Unit) {
        dbRef.child(path).get().addOnSuccessListener {
            action(ResultHandler.Success(it))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }

    fun getFilteredChildSnapshot(
        path: String,
        query: String,
        action: (ResultHandler<DataSnapshot>) -> Unit
    ) {
        dbRef.child(path).orderByChild(query).equalTo(true).get().addOnSuccessListener {
            action(ResultHandler.Success(it))
        }.addOnFailureListener {
            action(ResultHandler.Error(null, it.message!!))
        }
    }

//    fun getFilteredChildCount(
//        path: String,
//        query: String,
//        onSuccess: (DataSnapshot) -> Unit,
//        onError:(message:String)->Unit
//    ) {
//        dbRef.child(path).orderByChild(query).equalTo(true).get().addOnSuccessListener {
//            action(ResultHandler.Success(it))
//        }.addOnFailureListener {
//            action(ResultHandler.Error(null, it.message!!))
//        }
//    }

}