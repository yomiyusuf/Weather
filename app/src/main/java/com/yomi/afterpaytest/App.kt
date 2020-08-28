package com.yomi.afterpaytest

import android.app.Application
import com.yomi.afterpaytest.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(provideDependencies())
        }
    }

    fun provideDependencies() = appComponent
}