package com.example.cleanapp.ui.collect_details

import com.example.cleanapp.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class ChooserRepository @Inject constructor(
    private val dbRef: DatabaseReference,
    private val auth: FirebaseAuth
) {
    fun setOrderInDb(order:Order,action:(Boolean,String) -> Unit){
        dbRef.child("users")
            .child(auth.currentUser!!.uid)
            .child("order")
            .setValue(order) { error, _ ->
                val message = error?.message ?: ""
                val isValid = error != null
                action(isValid,message)
            }
    }

    fun getCategory(onLoad: (ResultHandler<List<Category>>) -> Unit) {
        dbRef.child("categories")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onLoad(ResultHandler.Success(snapshot.getValue<List<Category>>()))
                }

                override fun onCancelled(error: DatabaseError) {
                    onLoad(ResultHandler.Error(null, error.message))
                }
            })
    }

    fun getCities(onLoad: (ResultHandler<List<City>>) -> Unit) {
        dbRef.child("cities").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                onLoad(ResultHandler.Success(snapshot.getValue<List<City>>()))
            }

            override fun onCancelled(error: DatabaseError) {
                onLoad(ResultHandler.Error(null,error.message))
            }
        })
    }

    fun getRooms(onLoad: (ResultHandler<List<RoomCounter>>) -> Unit) {
        dbRef.child("rooms").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                onLoad(ResultHandler.Success(snapshot.getValue<List<RoomCounter>>()))
            }

            override fun onCancelled(error: DatabaseError) {
                onLoad(ResultHandler.Error(null,error.message))
            }
        })
    }


}