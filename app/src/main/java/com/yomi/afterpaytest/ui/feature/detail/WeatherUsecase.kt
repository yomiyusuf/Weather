package com.yomi.afterpaytest.ui.feature.detail

import com.yomi.afterpaytest.data.IRepository
import com.yomi.afterpaytest.network.WeatherData

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
class WeatherUsecase(private val repository: IRepository) {

    suspend fun getWeather(city: String): WeatherData {
        return repository.getWeather(city)
    }
}