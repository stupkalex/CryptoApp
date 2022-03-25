package com.stupkalex.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.stupkalex.cryptoapp.data.model.CoinInfoDto
import com.stupkalex.cryptoapp.databinding.CoinPriceListActivityBinding
import com.stupkalex.cryptoapp.domain.CoinInfo




class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    private val binding by lazy {
        CoinPriceListActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                startActivity(
                    CoinDetailInfo.newIntent(
                        this@CoinPriceListActivity,
                        coinInfo.fromSymbol
                    )
                )
            }
        }
        coinViewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

    }
}
