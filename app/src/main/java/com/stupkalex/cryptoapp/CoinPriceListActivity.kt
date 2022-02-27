package com.stupkalex.cryptoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.stupkalex.cryptoapp.adapter.CoinInfoAdapter
import com.stupkalex.cryptoapp.pojo.CoinPriceInfo


private lateinit var coinViewModel: CoinViewModel
private lateinit var rvCoinPriceList: RecyclerView

class CoinPriceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_price_list_activity)
        coinViewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(CoinViewModel::class.java)
        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)
        val adapter = CoinInfoAdapter()
        rvCoinPriceList.adapter = adapter
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                startActivity(
                    CoinDetailInfo.newIntent(
                        this@CoinPriceListActivity,
                        coinPriceInfo.fromsymbol
                    )
                )
            }
        }
        coinViewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }


}
