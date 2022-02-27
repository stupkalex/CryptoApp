package com.stupkalex.cryptoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.pojo.CoinPriceInfo

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCoinLogo = itemView.findViewById<ImageView>(R.id.ivCoinLogo)
        var textViewCoinName = itemView.findViewById<TextView>(R.id.textViewCoinName)
        var textViewCoinPrice = itemView.findViewById<TextView>(R.id.textViewCoinPrice)
        var textViewDataUpdate = itemView.findViewById<TextView>(R.id.textViewDataUpdate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        holder.textViewCoinName.text = coin.fromsymbol + " | " + coin.tosymbol
        holder.textViewCoinPrice.text = coin.price.toString()
        holder.textViewDataUpdate.text = "Время последнего обновления: " + coin.getFormattedDate()
        Picasso.get().load(coin.getFullImage()).into(holder.ivCoinLogo)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}
