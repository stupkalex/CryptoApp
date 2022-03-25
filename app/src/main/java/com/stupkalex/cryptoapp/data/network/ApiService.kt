package com.stupkalex.cryptoapp.data.network

import com.stupkalex.cryptoapp.data.model.CoinNamesListDto
import com.stupkalex.cryptoapp.data.model.CoinInfoJsonContainerDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinInfo (
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENT_USD
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey : String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSym : String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSym : String = CURRENT_USD
    ) : CoinInfoJsonContainerDto

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