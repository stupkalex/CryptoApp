package com.stupkalex.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stupkalex.cryptoapp.domain.CoinInfo

class ItemCallback : DiffUtil.ItemCallback<CoinInfo>(){
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}