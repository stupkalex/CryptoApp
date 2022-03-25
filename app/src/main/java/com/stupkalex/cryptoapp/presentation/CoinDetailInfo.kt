package com.stupkalex.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.R

private lateinit var coinViewModel: CoinViewModel


private lateinit var tvDetailPrice: TextView
private lateinit var tvDetailMinPrice: TextView
private lateinit var tvDetailMaxPrice: TextView
private lateinit var tvDetailLastDeal: TextView
private lateinit var tvDetailLastUpdate: TextView
private lateinit var tvDetailName: TextView
private lateinit var imageViewCoinLogo: ImageView

class CoinDetailInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail_info)

        tvDetailPrice = findViewById(R.id.tvDetailPrice)
        tvDetailMinPrice = findViewById(R.id.tvDetailMinPrice)
        tvDetailMaxPrice = findViewById(R.id.tvDetailMaxPrice)
        tvDetailLastDeal = findViewById(R.id.tvDetailLastDeal)
        tvDetailLastUpdate = findViewById(R.id.tvDetailLastUpdate)
        tvDetailName = findViewById(R.id.tvDetailName)
        imageViewCoinLogo = findViewById(R.id.imageViewCoinLogo)


        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fSym = intent.getStringExtra(EXTRA_FROM_SYMBOL)?: EMPTY_SYMBOL

        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        coinViewModel.getDetailInfo(fSym).observe(this){
            tvDetailPrice.text = String.format("%s %s", it.price.toString(), it.toSymbol)
            tvDetailMinPrice.text = String.format("%s %s", it.lowDay.toString(), it.lowDay)
            tvDetailMaxPrice.text = String.format("%s %s", it.highDay.toString(), it.toSymbol)
            tvDetailLastDeal.text = it.lastMarket
            tvDetailName.text = String.format("%s | %s", it.fromSymbol, it.toSymbol)
            tvDetailLastUpdate.text = it.lastUpdate
            Picasso.get().load(it.imageUrl).into(imageViewCoinLogo)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailInfo::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}