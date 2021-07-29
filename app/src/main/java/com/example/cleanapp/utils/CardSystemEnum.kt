package com.example.cleanapp.utils

enum class CardSystemEnum (val system: Char) {
    AMEX('3'),
    VISA('4'),
    MASTERCARD('5');

    companion object {
        fun toTypeList(): List<Char> {
            return listOf(
                CardSystemEnum.AMEX.system,
                CardSystemEnum.VISA.system,
                CardSystemEnum.MASTERCARD.system,
            )
        }
    }
}