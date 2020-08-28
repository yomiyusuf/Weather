package com.yomi.afterpaytest.data

import com.yomi.afterpaytest.network.WeatherData

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
interface IRepository {
    suspend fun getWeather(city: String): WeatherData
}