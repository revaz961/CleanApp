package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val phone: String? = null,

    @get:PropertyName("img_url")
    @set:PropertyName("img_url")
    var imgUrl: String? = null,

    @get:PropertyName("is_master")
    @set:PropertyName("is_master")
    var isMaster: Boolean? = null,

    @get:PropertyName("registration_date")
    @set:PropertyName("registration_date")
    var registrationDate:Long? = null
) : Parcelable