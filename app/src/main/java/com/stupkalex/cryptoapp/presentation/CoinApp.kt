package com.stupkalex.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.stupkalex.cryptoapp.data.database.workers.RefreshDataWorkerFactory
import com.stupkalex.cryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

class CoinApp: Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    @Inject
    lateinit var refreshDataWorkerFactory: RefreshDataWorkerFactory

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
               refreshDataWorkerFactory
            )
            .build()
    }

}