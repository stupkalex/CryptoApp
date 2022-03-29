package com.stupkalex.cryptoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.databinding.CoinItemBinding
import com.stupkalex.cryptoapp.domain.CoinInfo

class CoinInfoAdapter : ListAdapter<CoinInfo, CoinInfoViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = CoinItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder.binding) {
            textViewCoinName.text = String.format("%s | %s", coin.fromSymbol, coin.toSymbol)
            textViewCoinPrice.text = coin.price.toString()
            textViewDataUpdate.text = String.format(
                "Время последнего обновления: %s",
                coin.lastUpdate
            )
            Picasso.get().load(coin.imageUrl).into(ivCoinLogo)
        }
    }
}
