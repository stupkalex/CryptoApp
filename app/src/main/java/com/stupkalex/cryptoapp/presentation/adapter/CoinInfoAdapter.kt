package com.stupkalex.cryptoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.data.network.ApiFactory
import com.stupkalex.cryptoapp.domain.CoinInfo

class CoinInfoAdapter : ListAdapter<CoinInfo,CoinInfoViewHolder>(ItemCallback()) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        holder.textViewCoinName.text = String.format("%s | %s", coin.fromSymbol, coin.toSymbol)
        holder.textViewCoinPrice.text = coin.price.toString()
        holder.textViewDataUpdate.text = String.format(
            "Время последнего обновления: %s",
            coin.lastUpdate
        )
        Picasso.get().load(coin.imageUrl).into(holder.ivCoinLogo)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinInfo: CoinInfo)
    }
}
