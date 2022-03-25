package com.stupkalex.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stupkalex.cryptoapp.data.database.repository.CoinRepositoryImpl
import kotlinx.coroutines.launch

class CoinViewModel(application: Application): AndroidViewModel(application) {

   private val repository = CoinRepositoryImpl(application)

    val coinInfoList = repository.getCoinInfoList()

    fun getDetailInfo(fSym: String) = repository.getCoinInfo(fSym)

    init {
        viewModelScope.launch {
            repository.loadData()
        }
    }




}