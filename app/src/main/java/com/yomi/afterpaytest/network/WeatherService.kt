package com.yomi.afterpaytest.network

import com.yomi.afterpaytest.util.Endpoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yomi Joseph on 2020-08-27.
 */

interface WeatherService {

    @GET(Endpoints.WEATHER)
    suspend fun getWeather(@Query("q")city: String, @Query("appid")appid: String): Response<WeatherData>
}