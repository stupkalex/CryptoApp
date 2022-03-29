package com.stupkalex.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stupkalex.cryptoapp.data.database.repository.CoinRepositoryImpl
import com.stupkalex.cryptoapp.domain.ClearCoinListUseCase
import com.stupkalex.cryptoapp.domain.GetCoinInfoListUseCase
import com.stupkalex.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private var _limit: Int = DEFAULT_LIMIT
    private var _current: String = DEFAULT_CURRENT
    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    val clearCoinListUseCase = ClearCoinListUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun clearCoinList() {
        viewModelScope.launch {
            clearCoinListUseCase()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            loadDataUseCase(limit = _limit, tSym = _current)
        }
    }

    fun changeLimit(limit: Int){
        _limit = limit
    }

    fun changeCurrent(current: String){
        _current = current
    }

    init {
        loadData()
    }

    companion object {

        const val DEFAULT_CURRENT = "USD"
        const val DEFAULT_LIMIT = 10
    }


}