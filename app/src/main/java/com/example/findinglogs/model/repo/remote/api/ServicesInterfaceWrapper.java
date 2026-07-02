package com.example.findinglogs.model.repo.remote.api;

import com.example.findinglogs.model.model.GeoCity;
import com.example.findinglogs.model.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public abstract class ServicesInterfaceWrapper {

    public interface WeatherService {
        @GET("weather")
        Call<Weather> getWeather(@Query("lat") String latitude,
                                     @Query("lon") String longitude,
                                     @Query("appid") String appid);
    }

    public interface GeocodingService {
        @GET("geo/1.0/direct")
        Call<List<GeoCity>> searchCities(@Query("q") String query,
                                          @Query("limit") int limit,
                                          @Query("appid") String appid);
    }
}