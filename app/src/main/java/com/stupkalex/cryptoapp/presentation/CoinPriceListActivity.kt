package com.stupkalex.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.stupkalex.cryptoapp.data.model.CoinInfoDto
import com.stupkalex.cryptoapp.domain.CoinInfo


private lateinit var coinViewModel: CoinViewModel
private lateinit var rvCoinPriceList: RecyclerView

class CoinPriceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_price_list_activity)
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)
        val adapter = CoinInfoAdapter()
        rvCoinPriceList.adapter = adapter
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
