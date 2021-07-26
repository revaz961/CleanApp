package com.example.cleanapp.utils

enum class CardSystemEnum (val system: Int) {
    AMEX(3),
    VISA(4),
    MASTERCARD(5);

    companion object {
        fun toTypeList(): List<Int> {
            return listOf(
                CardSystemEnum.AMEX.system,
                CardSystemEnum.VISA.system,
                CardSystemEnum.MASTERCARD.system,
            )
        }
    }
}