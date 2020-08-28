package com.yomi.afterpaytest.ui.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomi.afterpaytest.network.WeatherData
import com.yomi.afterpaytest.ui.feature.detail.WeatherUsecase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Yomi Joseph on 2020-08-27.
 */
class WeatherViewModel(private val usecase: WeatherUsecase): ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError: LiveData<Boolean> = _loadingError

    fun getWeatherData(city: String) {
        viewModelScope.launch(errorHandler) {
            val data = withContext(Dispatchers.IO) {
                usecase.getWeather(city)
            }
            _weatherData.value = data
        }
    }

    private val errorHandler = CoroutineExceptionHandler{_, exception -> handelError(exception)}

    private fun handelError(exception: Throwable) {
        _loadingError.value = true
    }
}