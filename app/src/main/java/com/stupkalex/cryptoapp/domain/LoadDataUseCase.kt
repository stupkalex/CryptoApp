package com.stupkalex.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinRepository) {

    suspend operator fun invoke(limit: Int, tSym: String) = repository.loadData(limit, tSym)
}