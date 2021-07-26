package com.example.cleanapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    @get:PropertyName("card_number")
    @set:PropertyName("card_number")
    var cardNumber: String = "",

    @get:PropertyName("card_holder")
    @set:PropertyName("card_holder")
    var cardHolder: String = "",

    @get:PropertyName("expiration")
    @set:PropertyName("expiration")
    var expiration: String = "",

    @get:PropertyName("cvv")
    @set:PropertyName("cvv")
    var cvv: String = ""

) : Parcelable