package com.example.cleanapp.repository

import com.example.cleanapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

typealias OnAuth = (FirebaseUser?,String) -> Unit

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {

    fun logIn(email: String, password: String, action: OnAuth) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                action(auth.currentUser, task.exception?.message ?: "")
            }
    }

    fun register(email: String, password: String, action: OnAuth) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                action(auth.currentUser, task.exception?.message ?: "")
            }
    }

    fun setUserProfile(profile: User) {
        dbRef.child("users").child("${auth.currentUser!!.uid}").setValue(profile)
    }
}