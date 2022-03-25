package com.stupkalex.cryptoapp.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stupkalex.cryptoapp.R

class CoinInfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ivCoinLogo = itemView.findViewById<ImageView>(R.id.ivCoinLogo)
    var textViewCoinName = itemView.findViewById<TextView>(R.id.textViewCoinName)
    var textViewCoinPrice = itemView.findViewById<TextView>(R.id.textViewCoinPrice)
    var textViewDataUpdate = itemView.findViewById<TextView>(R.id.textViewDataUpdate)
}