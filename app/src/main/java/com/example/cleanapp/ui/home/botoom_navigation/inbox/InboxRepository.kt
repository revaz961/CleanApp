package com.example.cleanapp.ui.home.botoom_navigation.inbox

import com.example.cleanapp.models.Comment
import com.example.cleanapp.models.Master
import com.example.cleanapp.models.MasterCategory
import com.example.cleanapp.models.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

typealias OnLoad = (Master) -> Unit

class InboxRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) {
    fun getMessages(action: OnLoad) {
        val uid = auth.currentUser!!.uid
        dbRef.child("messages/$uid")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    dbRef.child("masters/${snapshot.key}").get().addOnSuccessListener {
                        val syncList = mutableListOf(false, false, false)
                        val chat = it.getValue<Chat>()
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
    }

    fun setMasters(master: Master, action: () -> Unit) {
        dbRef.child("masters").push().setValue(master) { error, ref ->
            action()
        }
    }
}