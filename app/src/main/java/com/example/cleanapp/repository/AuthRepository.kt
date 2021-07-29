package com.example.cleanapp.repository

import android.net.Uri
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.models.User
import com.example.cleanapp.user_data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

typealias OnAuth = (FirebaseUser?, String) -> Unit

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference,
    private val storage: StorageReference,
    private val userData: UserData,
) {

    fun logIn(email: String, password: String, action: OnAuth) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            getUser {
                if (it is ResultHandler.Success) {
                    userData.saveUser(it.data!!)
                    action(auth.currentUser, "")
                } else {
                    action(null, "Something was wrong!\nTry again.")
                }
            }
        }.addOnFailureListener {
            action(null, it.message!!)
        }
    }

    fun register(email: String, password: String, action: OnAuth) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                action(auth.currentUser, task.exception?.message ?: "")
            }
    }

    fun setUserProfile(profile: User) {
        dbRef.child("users").child(auth.currentUser!!.uid).setValue(profile)
    }

    fun uploadImage(uri: Uri) {
        if (auth.currentUser != null) {
            storage.child("images/${auth.currentUser!!.uid}/profile").putFile(uri)
        }
    }

    fun getUser(onLoad: (ResultHandler<User>) -> Unit) {
        val key = auth.currentUser?.uid

        dbRef.child("users/$key").get().addOnSuccessListener {
            val user = it.getValue<User>()
            user?.let { it1 -> onLoad(ResultHandler.Success(it1)) }
        }.addOnFailureListener {
            onLoad(ResultHandler.Error(null, it.message!!))
        }
    }
}