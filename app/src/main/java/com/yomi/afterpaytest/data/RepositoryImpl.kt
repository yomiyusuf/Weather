package com.yomi.afterpaytest.data

import android.util.Log
import com.yomi.afterpaytest.network.WeatherApiException
import com.yomi.afterpaytest.network.WeatherData
import com.yomi.afterpaytest.network.WeatherService

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
class RepositoryImpl(private val weatherService: WeatherService) : IRepository {
    override suspend fun getWeather(city: String): WeatherData {
        val resp = weatherService.getWeather(city, "a9fa45e53400d2c0cf1b2ddf946a6a28") // the appid should be injected during the construction of the retrofit mosule

        return if (resp.isSuccessful) {
            resp.body()!!
        } else {
            throw WeatherApiException(resolveErrorMessage(resp.errorBody().toString()))
        }
    }

    private fun resolveErrorMessage(error: String): String {
        Log.e("Error", error)
        return "api error"
    }
}