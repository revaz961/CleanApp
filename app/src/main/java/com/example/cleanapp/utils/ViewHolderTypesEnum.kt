package com.example.cleanapp.utils

enum class ReservationClickTypes(val type: Int) {
    SHOW_COMMENTS(0),
    CONTACT_MASTER(1),
    AVAILABILITY(2),
    CANCELLATION(3),
    REPORT(4),
    MORE_MASTERS(5)
}

enum class ReservationViewTypes(val type: Int) {
    HEADER(0),
    REVIEWS(1),
    LANGUAGES(2),
    INFO_EDIT(3),
    REPORT(4),
    MORE(5),
    CANCELLATION(6);

    companion object {
        fun typeToList(): List<Int> {
            return listOf(
                HEADER.type,
                REVIEWS.type,
                LANGUAGES.type,
                INFO_EDIT.type,
                CANCELLATION.type,
                REPORT.type,
                MORE.type
            )
        }
    }
}

enum class ConfirmationViewTypes(val type: Int) {
    HEADER(0),
    ARRIVAL_DETAILS(1),
    PRICE_DETAILS(2),
    CREDIT_CARDS(3),
    MESSAGE_MASTER(4),
    CANCELLATION(5),
    FOOTER(6);

    companion object {
        fun toTypeList(): List<Int> {
            return listOf(
                HEADER.type,
                ARRIVAL_DETAILS.type,
                PRICE_DETAILS.type,
                CREDIT_CARDS.type,
                MESSAGE_MASTER.type,
                CANCELLATION.type,
                FOOTER.type
            )
        }
    }
}
