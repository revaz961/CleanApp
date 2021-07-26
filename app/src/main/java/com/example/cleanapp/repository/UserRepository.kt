package com.example.cleanapp.repository

import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getUser(onLoad:(ResultHandler<User>)->Unit){
        val key = auth.currentUser?.uid

        dbRef.child("users/$key").get().addOnSuccessListener {
            val user = it.getValue<User>()
            user?.let { it1 -> onLoad(ResultHandler.Success(it1)) }
        }.addOnFailureListener {
            onLoad(ResultHandler.Error(null,it.message!!))
        }
    }
}