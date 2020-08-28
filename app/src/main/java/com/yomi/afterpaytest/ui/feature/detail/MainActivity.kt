package com.yomi.afterpaytest.ui.feature.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.yomi.afterpaytest.R
import com.yomi.afterpaytest.network.WeatherData
import com.yomi.afterpaytest.ui.feature.WeatherViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<WeatherViewModel>()
    private var city = "Manchester,GB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null)viewModel.getWeatherData(city)
        registerObservers()
        btn_enter.setOnClickListener {
            city = etxt_city.text.toString()
            viewModel.getWeatherData(city)
        }
    }

    private fun registerObservers() {
        viewModel.weatherData.observe(this, Observer {
            it?.let {
                Log.e("Weather", it.toString())
                handleUI(it)
            }
        })

        viewModel.loadingError.observe(this, Observer { handleErrorState(it) })
    }

    private fun handleUI(it: WeatherData) {
        label_name.text = city
        txt_temp.text = "${it.main.temp}K"
        txt_humidity.text = it.main.humidity.toString()
    }

    //handle error in UI
    private fun handleErrorState(isError: Boolean) {
        if (isError) Log.e("Weather", "Error")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
