package com.stupkalex.cryptoapp.data.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.stupkalex.cryptoapp.data.database.AppDatabase
import com.stupkalex.cryptoapp.data.database.mapper.CoinMapper
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.domain.CoinInfo
import com.stupkalex.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

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

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getCoinInfoAboutSymbol(fromSymbol)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinInfo(limit = 20)
                val fSym = mapper.mapNamesListToString(topCoins)
                val coinInfoContainer = apiService.getFullPriceList(fSym = fSym)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(coinInfoContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertCoinInfoList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}