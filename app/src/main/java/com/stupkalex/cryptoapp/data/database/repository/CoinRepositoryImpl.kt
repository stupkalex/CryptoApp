package com.stupkalex.cryptoapp.data.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.stupkalex.cryptoapp.data.database.AppDatabase
import com.stupkalex.cryptoapp.data.database.mapper.CoinMapper
import com.stupkalex.cryptoapp.data.database.workers.RefreshDataWorker
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.domain.CoinInfo
import com.stupkalex.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceListDao()
    private val mapper = CoinMapper()

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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(RefreshDataWorker.NAME,
        ExistingWorkPolicy.REPLACE,
        RefreshDataWorker.makeRequest())
    }
}