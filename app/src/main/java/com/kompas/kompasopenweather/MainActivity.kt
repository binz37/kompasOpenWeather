package com.kompas.kompasopenweather

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kompas.kompasopenweather.databinding.ActivityMain2Binding
import com.kompas.kompasopenweather.model.City
import com.kompas.kompasopenweather.utils.Resource
import com.kompas.kompasopenweather.viewmodel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            Timber.d("onCreate")
            binding = ActivityMain2Binding.inflate(layoutInflater)
            setContentView(binding.root)
            cityViewModel.getCity("Jakarta")
            setDataToUi()
        } catch (e: Exception) {
            Timber.v(e)
        }
    }

    private fun setDataToUi() {
        Timber.d("setDataToUi")
        cityViewModel.city.observe(this, { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    Timber.d("result SUCCESS")
                    binding.progressBar.visibility = View.GONE
                    response.data?.let { setSuccessData(it) }
                }

                Resource.Status.ERROR -> {
                    Timber.d("result ERROR")
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    Timber.d("on LOADING")
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setSuccessData(city: City) {
        Timber.d("setSuccessData")
        setImageWeatherToUi(city)
        setDataToTextUi(city)
    }

    private fun setDataToTextUi(city: City) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm aa", Locale.getDefault())
        val updated = "Last Updated at " + sdf.format(Date())

        val temp = String.format("%.1f", city.main.temp).toDouble().toString() + "\u2103"
        val strMinTemp =
            "Min. Temp: " + String.format("%.1f", city.main.temp_min).toDouble()
                .toString() + "\u2103"
        val strMaxTemp =
            "Max. Temp: " + String.format("%.1f", city.main.temp_max).toDouble()
                .toString() + " \u2103"
        val strPressure = city.main.pressure.toString() + "hpa"
        val strHumidity = city.main.humidity.toString() + "%"
        val strWindSpeed = city.wind.speed.toString() + "km/h"
        binding.tvWeather.text = city.weather[0].description
        binding.tvTemp.text = temp
        binding.tvTempMin.text = strMinTemp
        binding.tvTempMax.text = strMaxTemp
        binding.tvSunriseTime.text =
            getTime(city.sys.sunrise.toLong() * 1000)
        binding.tvSunsetTime.text =
            getTime(city.sys.sunset.toLong() * 1000)
        binding.tvWindSpeed.text = strWindSpeed
        binding.tvPressureInfo.text = strPressure
        binding.tvHumidityInfo.text = strHumidity
        binding.tvLastUpdated.text = updated
    }

    private fun setImageWeatherToUi(city: City) {
        if (city.weather[0].description == "clear sky" || city.weather[0].description == "mist") {
            Glide.with(this)
                .load(R.drawable.clouds)
                .into(binding.image)
        } else {
            if (city.weather[0].description == "haze"
                || city.weather[0].description == "overcast clouds"
                || city.weather[0].description == "fog"
                || city.weather[0].description == "scattered clouds") {
                Glide.with(this)
                    .load(R.drawable.haze)
                    .into(binding.image)
            } else {
                if (city.weather[0].description.contains("rain")) {
                    Glide.with(this)
                        .load(R.drawable.rain)
                        .into(binding.image)
                }
            }
        }
    }

    private fun getTime(long: Long): String {
        val sdf = SimpleDateFormat("HH:mm aa", Locale.getDefault())
        return sdf.format(Date(long))
    }
}