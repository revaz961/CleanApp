package com.example.cleanapp.utils

enum class ReservationClickTypes (val type : Int) {
    SHOW_COMMENTS(0),
    CONTACT_MASTER(1),
    AVAILABILITY(2),
    CANCELLATION(3),
    REPORT(4),
    MORE_MASTERS(5),
    RESERVE(6)
}

enum class ReservationViewTypes (val type: Int) {
    HEADER(0),
    REVIEWS(1),
    LANGUAGES(2),
    INFO_EDIT(3),
    REPORT(4),
    MORE(5)
}