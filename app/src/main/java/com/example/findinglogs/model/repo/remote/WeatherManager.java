package com.example.findinglogs.model.repo.remote;


import androidx.annotation.NonNull;

import com.example.findinglogs.BuildConfig;
import com.example.findinglogs.model.model.GeoCity;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.repo.remote.api.GeoCallback;
import com.example.findinglogs.model.repo.remote.api.ServicesInterfaceWrapper;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherManager {
    private static final String TAG = WeatherManager.class.getSimpleName();

    public WeatherManager() {
        if (Logger.ISLOGABLE) Logger.d(TAG, "WeatherManager()");
    }

    public void retrieveForecast(String localization, WeatherCallback callback) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "retrieveForecast()");
        String apiKey = BuildConfig.WEATHER_API_KEY;
        String[] split = localization.split(",");
        String lat = split[0];
        String lon = split[1];

        ConnectionManager.getInstance()
                .getWeatherConnection()
                .create(ServicesInterfaceWrapper.WeatherService.class)
                .getWeather(lat, lon, apiKey).enqueue(new Callback<>() {

                    @Override
                    public void onResponse(@NonNull Call<Weather> call,
                                           @NonNull Response<Weather> resp) {
                        if (resp.isSuccessful() && resp.code() == HttpsURLConnection.HTTP_OK) {
                            assert resp.body() != null;
                            callback.onSuccess(resp.body());
                        } else {
                            if (Logger.ISLOGABLE)
                                Logger.w(TAG, "getForecast: status:" + resp.code());
                            callback.onFailure(String.valueOf(resp.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Weather> call,
                                          @NonNull Throwable throwable) {
                        callback.onFailure(String.valueOf(throwable.getMessage()));
                    }
                });
    }

    public void searchCities(String query, GeoCallback callback) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "searchCities()");
        String apiKey = BuildConfig.WEATHER_API_KEY;

        ConnectionManager.getInstance()
                .getGeocodingConnection()
                .create(ServicesInterfaceWrapper.GeocodingService.class)
                .searchCities(query, 5, apiKey).enqueue(new Callback<>() {

                    @Override
                    public void onResponse(@NonNull Call<List<GeoCity>> call,
                                           @NonNull Response<List<GeoCity>> resp) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            callback.onSuccess(resp.body());
                        } else {
                            callback.onFailure(String.valueOf(resp.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<GeoCity>> call,
                                          @NonNull Throwable throwable) {
                        callback.onFailure(String.valueOf(throwable.getMessage()));
                    }
                });
    }
}