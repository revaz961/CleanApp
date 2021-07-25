package com.example.cleanapp.ui.sign_up_master

import com.example.cleanapp.models.Category
import com.example.cleanapp.models.City
import com.example.cleanapp.models.Master
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

    fun getCategory(action: (List<Category>) -> Unit) {
        dbRef.child("categories").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<List<Category>>()?.let { action(it) }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun getLanguages(action: (List<String>) -> Unit) {
        dbRef.child("languages").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<List<String>>()?.let { action(it) }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun setMaster(master: Master, action: () -> Unit) {
        val key = master.user!!.uid
        val user = master.user!!
        val childUpdates = hashMapOf<String, Any>("/masters/$key" to master, "/users/$key" to user)
        dbRef.updateChildren(childUpdates).addOnSuccessListener {
            dbRef.child("masters_category/$key").setValue(master.categories).addOnSuccessListener {
                action()
            }

            val city = master.city!!.cityEn.lowercase()
            val map = hashMapOf<String,Any>()
            master.categories?.forEach {
                val category = it.category?.categoryEn?.lowercase()?.replace(' ','_')
                map["${city}_$category"] = true
            }

            dbRef.child("master_city_category/$key").updateChildren(map)
        }
    }
}