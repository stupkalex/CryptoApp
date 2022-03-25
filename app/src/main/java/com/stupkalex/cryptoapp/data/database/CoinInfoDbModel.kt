package com.stupkalex.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String? = null,
    val price: Double = 0.0,
    val lastUpdate: Long = 0,
    val highDay: Double = 0.0,
    val lowDay: Double = 0.0,
    val lastMarket: String? = null,
    val imageUrl: String? = null
)