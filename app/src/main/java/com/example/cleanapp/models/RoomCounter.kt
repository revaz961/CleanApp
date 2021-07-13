package com.example.cleanapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomCounter(val room: String = "", var count: Int = 0):Parcelable