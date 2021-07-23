package com.example.cleanapp.ui.home.master_results

import com.example.cleanapp.models.Comment
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (Master) -> Unit

class MasterResultsRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getMasters(query: String, action: OnLoad) {

        dbRef.child("master_city_category").orderByChild(query).equalTo(true)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    dbRef.child("masters/${snapshot.key}").get().addOnSuccessListener {
                        val syncList = mutableListOf(false, false, false)
                        val master = it.getValue<Master>()
                        val key = it.key
                        dbRef.child("reviews/$key").get().addOnSuccessListener {
                            master?.reviews = it.getValue<Review>()
                            syncList[0] = true
                            if (syncList.all { it })
                                master?.let { it1 -> action(it1) }
                        }

                        dbRef.child("reviews/$key/comments").orderByKey().limitToFirst(1).get()
                            .addOnSuccessListener {
                                master?.lastComments = it.getValue<List<Comment>>()
                                syncList[1] = true
                                if (syncList.all { it })
                                    master?.let { it1 -> action(it1) }
                            }

                        dbRef.child("masters_category/$key").get().addOnSuccessListener {
                            master?.categories = it.getValue<List<MasterCategory>>()
                            syncList[2] = true
                            if (syncList.all { it })
                                master?.let { it1 -> action(it1) }
                        }
                    }
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })

//        dbRef.child("masters").orderByValue().limitToFirst(10)
//            .addChildEventListener(object : ChildEventListener {
//                val syncList = mutableListOf(false, false)
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    val master = snapshot.getValue<Master>()
//                    val key = snapshot.key
//                    dbRef.child("reviews/$key").get().addOnSuccessListener {
//                        master?.reviews = it.getValue<Review>()
//                        syncList[0] = true
//                        if (syncList.all { it })
//                            master?.let { it1 -> action(it1) }
//                    }
//
//                    dbRef.child("masters_category/$key").get().addOnSuccessListener {
//                        master?.categories = it.getValue<List<MasterCategory>>()
//                        syncList[1] = true
//                        if (syncList.all { it })
//                            master?.let { it1 -> action(it1) }
//                    }
//                }
//
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {}
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//
//                override fun onCancelled(error: DatabaseError) {}
//
//            })
    }

    fun setMasters(master: Master, action: () -> Unit) {
        dbRef.child("masters").push().setValue(master) { error, ref ->
            action()
        }
    }
}