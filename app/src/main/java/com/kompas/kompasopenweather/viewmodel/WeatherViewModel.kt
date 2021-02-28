package com.kompas.kompasopenweather.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kompas.kompasopenweather.model.City
import com.kompas.kompasopenweather.repository.WeatherRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    val weatherResponse: MutableLiveData<City> = MutableLiveData()
    private val searchChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        searchChannel.offer(search)
    }

    fun getCityData() {
        viewModelScope.launch {
            searchChannel.asFlow()
                .flatMapLatest { search ->
                    weatherRepository.getCityData(search)
                }.catch { e ->
                    Timber.e(e)
                }.collect { response ->
                    weatherResponse.value = response
                }
        }
    }


}