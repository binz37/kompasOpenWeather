package com.kompas.kompasopenweather.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.kompas.kompasopenweather.model.City
import com.kompas.kompasopenweather.repository.CityRepository
import com.kompas.kompasopenweather.utils.Resource

class CityViewModel @ViewModelInject constructor(
    private val repository: CityRepository
) : ViewModel() {
    private val _q = MutableLiveData<String>()
    private val _city = _q.switchMap { q -> repository.getCity(q) }
    val city: LiveData<Resource<City>> = _city

    fun getCity(q: String) {
        _q.value = q
    }
}