package com.example.cleanapp.ui.home.botoom_navigation.explore

import com.example.cleanapp.models.Master
import com.example.cleanapp.models.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (List<Master>) -> Unit

class ExploreRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getMasters(action: OnLoad) {

        dbRef.child("masters").get().addOnSuccessListener {
            val masters = mutableListOf<Master>()
            for (masterSnapshot in it.children)
                masterSnapshot.getValue<Master>()?.let { masters.add(it) }

            getReviews(masters, action)
        }
    }

    private fun getReviews(masters: MutableList<Master>, action: OnLoad) {

        dbRef.child("reviews").get().addOnSuccessListener { dataSnapshot ->
            for (reviewSnapshot in dataSnapshot.children)
                masters.find { it.uid == reviewSnapshot.key }
                    ?.let {
                        it.recentReviews = reviewSnapshot.getValue<Review>()
                    }
            action(masters)
        }
    }

    fun setMasters(master: Master, action: () -> Unit) {
        dbRef.child("masters").push().setValue(master) { error, ref ->
            action()
        }
    }
}