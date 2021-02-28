package com.kompas.kompasopenweather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kompas.kompasopenweather.databinding.ActivityMain2Binding
import com.kompas.kompasopenweather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        weatherViewModel.getCityData()
        weatherViewModel.setSearchQuery("Jakarta")
        setDataToUi()
    }

    private fun setDataToUi() {
        Timber.d("setDataToUi")
        weatherViewModel.weatherResponse.observe(this, { response ->
            if (response.weather[0].description == "clear sky" || response.weather[0].description == "mist") {
                Glide.with(this)
                    .load(R.drawable.clouds)
                    .into(binding.image)
            } else
                if (response.weather[0].description == "haze" || response.weather[0].description == "overcast clouds" || response.weather[0].description == "fog") {
                    Glide.with(this)
                        .load(R.drawable.haze)
                        .into(binding.image)
                } else {
                    if (response.weather[0].description.contains("rain")) {
                        Glide.with(this)
                            .load(R.drawable.rain)
                            .into(binding.image)
                    }
                }

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm aa", Locale.getDefault())
            val updated = "Last Updated at " + sdf.format(Date())

            val temp = String.format("%.1f", response.main.temp).toDouble().toString() + "\u2103"
            val strMinTemp =
                "Min. Temp: " + String.format("%.1f", response.main.temp_min).toDouble()
                    .toString() + "\u2103"
            val strMaxTemp =
                "Max. Temp: " + String.format("%.1f", response.main.temp_max).toDouble()
                    .toString() + " \u2103"
            val strPressure = response.main.pressure.toString() + "hpa"
            val strHumidity = response.main.humidity.toString() + "%"
            val strWindSpeed = response.wind.speed.toString() + "km/h"
            binding.tvWeather.text = response.weather[0].description
            binding.tvTemp.text = temp
            binding.tvTempMin.text = strMinTemp
            binding.tvTempMax.text = strMaxTemp
            binding.tvSunriseTime.text =
                getTime(response.sys.sunrise.toLong() * 1000)
            binding.tvSunsetTime.text =
                getTime(response.sys.sunset.toLong() * 1000)
            binding.tvWindSpeed.text = strWindSpeed
            binding.tvPressureInfo.text = strPressure
            binding.tvHumidityInfo.text = strHumidity
            binding.tvLastUpdated.text = updated
        })
    }

    private fun getTime(long: Long): String {
        val sdf = SimpleDateFormat("HH:mm aa", Locale.getDefault())
        return sdf.format(Date(long))
    }
}