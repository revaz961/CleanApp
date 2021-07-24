package com.example.cleanapp.ui.sign_up_master

import com.example.cleanapp.models.City
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class SignUpMasterRepository @Inject constructor(private val dbRef: DatabaseReference) {
    fun getCities(action: (List<City>) -> Unit) {
        dbRef.child("cities").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<List<City>>()?.let { action(it) }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}