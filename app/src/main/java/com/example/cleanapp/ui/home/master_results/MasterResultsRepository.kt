package com.example.cleanapp.ui.home.master_results

import com.example.cleanapp.models.Comment
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.Review
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (List<Master>) -> Unit

class MasterResultsRepository @Inject constructor(
    private val dbRef: DatabaseReference
) {
    fun getMasters(query: String, action: OnLoad) {
        var masterCount = 0
        val masters = mutableListOf<Master>()

        dbRef.child("master_city_category").orderByChild(query).equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    masterCount = snapshot.childrenCount.toInt()

                    dbRef.child("master_city_category").orderByChild(query).equalTo(true)
                        .addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                dbRef.child("masters/${snapshot.key}").get()
                                    .addOnSuccessListener { dataSnapshot ->
                                        val syncList = mutableListOf(false, false, false)
                                        val master = dataSnapshot.getValue<Master>()
                                        masters.add(master!!)
                                        val key = dataSnapshot.key

                                        dbRef.child("reviews/$key").get().addOnSuccessListener {
                                            master.reviews = it.getValue<Review>()
                                        }.addOnCompleteListener {
                                            syncList[0] = true
                                            if (syncList.all { it } && masters.size == masterCount)
                                                action(masters)
                                        }

                                        dbRef.child("reviews/$key/comments").orderByKey()
                                            .limitToFirst(1).get()
                                            .addOnSuccessListener {
                                                master.lastComments = it.getValue<List<Comment>>()
                                            }.addOnCompleteListener {
                                                syncList[1] = true
                                                if (syncList.all { it } && masters.size == masterCount)
                                                    action(masters)
                                            }

                                        dbRef.child("masters_category/$key").get()
                                            .addOnSuccessListener {
                                                master.categories =
                                                    it.getValue<List<MasterCategory>>()
                                            }.addOnCompleteListener {
                                                syncList[2] = true
                                                if (syncList.all { it } && masters.size == masterCount)
                                                    action(masters)
                                            }
                                    }
                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                            }

                            override fun onChildRemoved(snapshot: DataSnapshot) {}

                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                }

                override fun onCancelled(error: DatabaseError) {}

            })
    }

    fun setMasters(master: Master, action: () -> Unit) {
        dbRef.child("masters").push().setValue(master) { error, ref ->
            action()
        }
    }
}