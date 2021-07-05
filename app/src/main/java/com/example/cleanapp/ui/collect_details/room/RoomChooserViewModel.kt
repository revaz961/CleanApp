package com.example.cleanapp.ui.collect_details.room

import androidx.lifecycle.ViewModel
import com.example.cleanapp.models.RoomCounter

class RoomChooserViewModel : ViewModel() {
    val roomCounters = mutableListOf<RoomCounter>(
        RoomCounter("Bedroom", 0),
        RoomCounter("Kitchen", 0),
        RoomCounter("Living Room", 0),
        RoomCounter("Entrance", 0),
        RoomCounter("Additional Rooms", 0),
        RoomCounter("Balcony", 0),
        RoomCounter("Garden", 0)
    )
}