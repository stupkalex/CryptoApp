package com.stupkalex.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.stupkalex.cryptoapp.databinding.ActivityCoinDetailInfoBinding
import com.stupkalex.cryptoapp.databinding.FragmentCoinDetailInfoBinding
import java.lang.RuntimeException

class CoinDetailFragment : Fragment() {

    private lateinit var coinViewModel: CoinViewModel

    private var _binding: FragmentCoinDetailInfoBinding? = null
    private val binding: FragmentCoinDetailInfoBinding
        get() = _binding ?: throw RuntimeException("Fragment not found")

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fSym = getSymbol()
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        coinViewModel.getDetailInfo(fSym).observe(viewLifecycleOwner) {
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

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_SYMBOL)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}