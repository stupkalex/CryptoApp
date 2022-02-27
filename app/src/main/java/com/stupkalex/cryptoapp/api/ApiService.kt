package com.stupkalex.cryptoapp.api

import com.stupkalex.cryptoapp.pojo.CoinInfoListOfData
import com.stupkalex.cryptoapp.pojo.CoinPriceInfoRawDate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinInfo (
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENT_USD
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey : String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSym : String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSym : String = CURRENT_USD
    ) : Single<CoinPriceInfoRawDate>

    companion object{
        const val QUERY_PARAM_LIMIT = "limit"
        const val QUERY_PARAM_TO_SYMBOL= "tsym"
        const val QUERY_PARAM_API_KEY = "api_key"
        const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        const val CURRENT_USD = "USD"
        const val API_KEY = "278010fb7eb7e733cceb82fa3b5b5461325769490a4794b9018e2f6d47f8ff6b"
    }
}