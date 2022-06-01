package com.stupkalex.cryptoapp.di

import android.app.Application
import com.stupkalex.cryptoapp.data.database.AppDatabase
import com.stupkalex.cryptoapp.data.database.CoinInfoDao
import com.stupkalex.cryptoapp.data.database.repository.CoinRepositoryImpl
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.data.network.ApiService
import com.stupkalex.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideCoinInfoDao(application: Application): CoinInfoDao{
            return AppDatabase.getInstance(application).coinPriceListDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }
    }
}