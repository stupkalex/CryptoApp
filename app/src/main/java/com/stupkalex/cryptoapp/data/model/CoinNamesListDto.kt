package com.stupkalex.cryptoapp.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class CoinNamesListDto (
    @SerializedName("Data")
    @Expose
    val nameDtos : List<CoinNameContainerDto>? = null
    )