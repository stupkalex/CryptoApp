package com.stupkalex.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.stupkalex.cryptoapp.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindViewModel(viewModel: CoinViewModel): ViewModel
}