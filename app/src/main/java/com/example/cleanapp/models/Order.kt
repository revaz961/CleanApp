package com.example.cleanapp.models

data class Order(
    var cityId: City? = null,
    var categoryId: Category? = null,
    var date: Long? = null,
    var roomCount: List<RoomCounter>? = null
)
