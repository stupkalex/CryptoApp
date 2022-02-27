package com.stupkalex.cryptoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

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

        coinViewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(CoinViewModel::class.java)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        var fSym = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        coinViewModel.getDetailInfo(fSym!!).observe(this, Observer {
            tvDetailPrice.text = it.price.toString() + " " + it.tosymbol
            tvDetailMinPrice.text = it.lowday.toString() + " " + it.tosymbol
            tvDetailMaxPrice.text = it.highday.toString() + " " + it.tosymbol
            tvDetailLastDeal.text = it.lastmarket
            tvDetailName.text = it.fromsymbol + " | " + it.tosymbol
            tvDetailLastUpdate.text = it.getFormattedDate()
                Picasso.get().load(it.getFullImage()).into(imageViewCoinLogo)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailInfo::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}