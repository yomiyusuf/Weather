package com.yomi.afterpaytest.di

import com.google.gson.GsonBuilder
import com.yomi.afterpaytest.data.IRepository
import com.yomi.afterpaytest.data.RepositoryImpl
import com.yomi.afterpaytest.network.WeatherService
import com.yomi.afterpaytest.ui.feature.WeatherViewModel
import com.yomi.afterpaytest.ui.feature.detail.WeatherUsecase
import com.yomi.afterpaytest.util.Endpoints
import com.yomi.afterpaytest.util.TIME_OUT
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Yomi Joseph on 2020-08-27.
 */

val repositoryModule = module {
    factory<IRepository> { RepositoryImpl(get()) }
}

val usecaseModule = module {
    factory { WeatherUsecase(get()) }
}

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
}
val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(androidContext().cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .cache(myCache)
            .addInterceptor{ chain ->
                val request = chain.request()
                //add default caching. Endpoint that should not be cached should be annotated with @Headers
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(Endpoints.BASE_URL).build()
    }

    factory { get<Retrofit>().create(WeatherService::class.java) }
}