package com.stupkalex.cryptoapp.data.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.stupkalex.cryptoapp.data.database.AppDatabase
import com.stupkalex.cryptoapp.data.database.mapper.CoinMapper
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.domain.CoinInfo
import com.stupkalex.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(private val application: Application): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceListDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getCoinInfoList()){
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun loadData(limit: Int, tSym: String) {
        val topCoins = apiService.getTopCoinInfo(limit = limit)
        val fSym = mapper.mapNamesListToString(topCoins)
        val coinInfoContainer = apiService.getFullPriceList(fSym = fSym, tSym = tSym)
        val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(coinInfoContainer)
        val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
        coinInfoDao.insertCoinInfoList(dbModelList)
    }

    override suspend fun clearCoinList() {
        coinInfoDao.clearCoinList()
    }
}