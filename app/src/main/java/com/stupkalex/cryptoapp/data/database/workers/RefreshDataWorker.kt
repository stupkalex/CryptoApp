package com.stupkalex.cryptoapp.data.database.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.stupkalex.cryptoapp.data.database.AppDatabase
import com.stupkalex.cryptoapp.data.database.CoinInfoDao
import com.stupkalex.cryptoapp.data.database.mapper.CoinMapper
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
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

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}