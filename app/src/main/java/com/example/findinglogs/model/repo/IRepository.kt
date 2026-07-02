package com.example.findinglogs.model.repo

import com.example.findinglogs.model.repo.remote.api.WeatherCallback

interface IRepository {
    fun retrieveForecast(latLon: String, callback: WeatherCallback)
    fun saveString(key: String, value: String)
    fun readString(key: String): String
    fun getLocalizations(): Map<String, String>
    fun addCity(coordinates: String)
}
