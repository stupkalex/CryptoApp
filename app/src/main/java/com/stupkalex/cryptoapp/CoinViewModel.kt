package com.stupkalex.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.stupkalex.cryptoapp.api.ApiFactory
import com.stupkalex.cryptoapp.database.AppDatabase
import com.stupkalex.cryptoapp.pojo.CoinPriceInfo
import com.stupkalex.cryptoapp.pojo.CoinPriceInfoRawDate
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)

    val priceList = db.coinPriceListDao().getPriceList()

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo>{
        return db.coinPriceListDao().getPriceInfoAboutSymbol(fSym)
    }
    init {
        loadData()
    }


    fun loadData(){
    val disposable = ApiFactory.apiService.getTopCoinInfo()
        .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") as String }
        .flatMap { ApiFactory.apiService.getFullPriceList(fSym = it) }
        .map { getPriceListFromRawData(it) }
        .delaySubscription(5, TimeUnit.SECONDS)
        .repeat()
        .retry()
        .subscribeOn(Schedulers.io())
        .subscribe({
            db.coinPriceListDao().insertPriceList(it)
        },{
            Log.d("TEST_TO_LOADING_DATA", it.message.toString())
        })
    compositeDisposable.add(disposable)
}

    fun getPriceListFromRawData(
        coinPriceInfoRawDate: CoinPriceInfoRawDate):
            List<CoinPriceInfo>{
        val result = ArrayList<CoinPriceInfo>()
        if(coinPriceInfoRawDate == null) return result
        val jsonObject = coinPriceInfoRawDate.coinPriceInfoJsonObject as JsonObject
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet){
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for(currentKey in currencyKeySet){
                val priceInfo = Gson().fromJson(currencyJson.getAsJsonObject(currentKey),CoinPriceInfo::class.java)
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}