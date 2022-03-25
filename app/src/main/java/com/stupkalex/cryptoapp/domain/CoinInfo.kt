package com.stupkalex.cryptoapp.domain

data class CoinInfo(
    val fromSymbol: String,
    val toSymbol: String? = null,
    val price: Double = 0.0,
    val lastUpdate: String?,
    val highDay: Double = 0.0,
    val lowDay: Double = 0.0,
    val lastMarket: String? = null,
    val imageUrl: String? = null
)
