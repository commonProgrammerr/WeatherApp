package com.example.findinglogs.model.repo

import com.example.findinglogs.model.repo.remote.api.GeoCallback
import com.example.findinglogs.model.repo.remote.api.WeatherCallback

interface IRepository {
    fun retrieveForecast(latLon: String, callback: WeatherCallback)
    fun saveString(key: String, value: String)
    fun readString(key: String): String
    fun getLocalizations(): Map<String, String>
    fun addCity(name: String, coordinates: String)
    fun removeCity(key: String)
    fun searchCities(query: String, callback: GeoCallback)
}
