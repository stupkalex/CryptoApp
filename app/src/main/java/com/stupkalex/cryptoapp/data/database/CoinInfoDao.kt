package com.stupkalex.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stupkalex.cryptoapp.data.model.CoinInfoDto

@Dao
interface CoinInfoDao {

    @Query("SELECT * FROM full_price_list ORDER BY price DESC")
    fun getCoinInfoList() : LiveData<List<CoinInfoDbModel>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinInfoList(priceList: List<CoinInfoDbModel>)

    @Query("DELETE FROM full_price_list")
    suspend fun clearCoinList()
}