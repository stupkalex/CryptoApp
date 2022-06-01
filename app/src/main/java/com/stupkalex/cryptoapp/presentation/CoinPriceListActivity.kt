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
import javax.inject.Inject


class CoinPriceListActivity : AppCompatActivity() {

   @Inject
   lateinit var viewModuleFactory: ViewModelFactory

    private val component by lazy{
        (application as CoinApp).component
    }

    private val binding by lazy {
        CoinPriceListActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val coinViewModel =  ViewModelProvider(this, viewModuleFactory)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter
        adapter.onCoinClickListener = launchOnClickListener()
        coinViewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchOnClickListener() =  object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                if (isOnePadMode()) {
                    startActivity(
                        CoinDetailInfo.newIntent(
                            this@CoinPriceListActivity,
                            coinInfo.fromSymbol
                        )
                    )
                } else {
                    supportFragmentManager.popBackStack()
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(
                            R.id.priceListActivityContainer,
                            CoinDetailFragment.newInstance(coinInfo.fromSymbol)
                        )
                        .commit()
                }
            }
    }

    private fun isOnePadMode(): Boolean {
        return binding.priceListActivityContainer == null
    }
}
