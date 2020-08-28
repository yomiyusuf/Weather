package com.yomi.afterpaytest.network

import com.google.gson.annotations.SerializedName

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
data class WeatherData(
    @SerializedName("main")
    val main: MainData
)

data class MainData(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("humidity")
    val humidity: Double
)
