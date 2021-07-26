package com.example.cleanapp.ui.home.master_results

import com.example.cleanapp.models.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (ResultHandler<List<Master>>) -> Unit

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
                                        master?.let { masters.add(it) }
                                        val key = dataSnapshot.key

                                        dbRef.child("reviews/$key").get().addOnSuccessListener {
                                            master?.reviews = it.getValue<Review>()
                                        }.addOnCompleteListener {
                                            syncList[0] = true
                                            if (syncList.all { it } && masters.size == masterCount)
                                                action(ResultHandler.Success(masters))
                                        }

                                        dbRef.child("reviews/$key/comments").orderByKey()
                                            .limitToFirst(1).get()
                                            .addOnSuccessListener {
                                                master?.lastComments = it.getValue<List<Comment>>()
                                            }.addOnCompleteListener {
                                                syncList[1] = true
                                                if (syncList.all { it } && masters.size == masterCount)
                                                    action(ResultHandler.Success(masters))
                                            }

                                        dbRef.child("masters_category/$key").get()
                                            .addOnSuccessListener {
                                                master?.categories =
                                                    it.getValue<List<MasterCategory>>()
                                            }.addOnCompleteListener {
                                                syncList[2] = true
                                                if (syncList.all { it } && masters.size == masterCount)
                                                    action(ResultHandler.Success(masters))
                                            }
                                    }
                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {}

                            override fun onChildRemoved(snapshot: DataSnapshot) {}

                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {}

                            override fun onCancelled(error: DatabaseError) {
                                action(ResultHandler.Error(null, error.message))
                            }
                        })
                }

                override fun onCancelled(error: DatabaseError) {
                    action(ResultHandler.Error(null, error.message))
                }
            })
    }
}