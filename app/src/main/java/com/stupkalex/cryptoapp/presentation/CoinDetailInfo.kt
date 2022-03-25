package com.stupkalex.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.databinding.ActivityCoinDetailInfoBinding

class CoinDetailInfo : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fSym = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        coinViewModel.getDetailInfo(fSym).observe(this) {
            with(binding) {
                tvDetailPrice.text = String.format("%s %s", it.price.toString(), it.toSymbol)
                tvDetailMinPrice.text = String.format("%s %s", it.lowDay.toString(), it.lowDay)
                tvDetailMaxPrice.text = String.format("%s %s", it.highDay.toString(), it.toSymbol)
                tvDetailLastDeal.text = it.lastMarket
                tvDetailName.text = String.format("%s | %s", it.fromSymbol, it.toSymbol)
                tvDetailLastUpdate.text = it.lastUpdate
                Picasso.get().load(it.imageUrl).into(imageViewCoinLogo)
            }
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