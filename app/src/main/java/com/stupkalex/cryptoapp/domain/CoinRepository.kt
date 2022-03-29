package com.stupkalex.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    suspend fun loadData(limit: Int, tSym: String)

    suspend fun clearCoinList()
}