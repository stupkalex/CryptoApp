package com.stupkalex.cryptoapp.domain

class ClearCoinListUseCase (private val repository: CoinRepository)
{
    suspend operator fun invoke() = repository.clearCoinList()

}