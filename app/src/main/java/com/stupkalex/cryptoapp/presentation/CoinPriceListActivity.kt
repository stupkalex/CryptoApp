package com.stupkalex.cryptoapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stupkalex.cryptoapp.R
import com.stupkalex.cryptoapp.databinding.*
import com.stupkalex.cryptoapp.presentation.adapter.CoinInfoAdapter


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    private val binding by lazy {
        MainLayoutBinding.inflate(layoutInflater)
    }

    private val currenciesButtons by lazy {
        mutableListOf<com.google.android.material.button.MaterialButton>().apply {
            add(binding.buttonGbr)
            add(binding.buttonIdr)
            add(binding.buttonJpy)
            add(binding.buttonRub)
            add(binding.buttonUsd)
            add(binding.buttonEur)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter
        coinViewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
        setupSpinnerAdapter()
        setCurrencyButtonClickListener()
    }

    private fun setCurrencyButtonClickListener(){
        for (button in currenciesButtons) {
            button.setOnClickListener {
                with(coinViewModel){
                    clearCoinList()
                    changeCurrent(button.text.toString())
                    loadData()
                }
                updateButton(button)
            }
        }
    }

    private fun setupSpinnerAdapter(){
        var spinnerAdapter = ArrayAdapter.createFromResource(this,
            R.array.limit, android.R.layout.simple_spinner_item).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        with(binding.spinner){
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    with(coinViewModel){
                        clearCoinList()
                        changeLimit(binding.spinner.selectedItem.toString().toInt())
                        loadData()
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            setSelection(1)
        }
    }


    private fun updateButton(selectedButton: com.google.android.material.button.MaterialButton){
        for (button in currenciesButtons) {
            button.setBackgroundColor(resources.getColor(R.color.white_button_background))
            button.setTextColor(resources.getColor(R.color.black))
        }
        selectedButton.setBackgroundColor(resources.getColor(R.color.selected_background))
        selectedButton.setTextColor(resources.getColor(R.color.selected_text))
    }


}
