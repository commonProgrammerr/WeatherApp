package com.example.findinglogs.model.model;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Weather {
    private WeatherInfo main;
    private String name;
    private List<WeatherDetail> weather = new ArrayList<>();
    private boolean isCurrentLocation;

    public Weather() {
    }

    public WeatherInfo getMain() {
        return main;
    }

    public void setMain(WeatherInfo main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDetail> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDetail> weather) {
        this.weather = weather;
    }

    public boolean isCurrentLocation() {
        return isCurrentLocation;
    }

    public void setCurrentLocation(boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather1 = (Weather) o;
        return isCurrentLocation == weather1.isCurrentLocation
                && Objects.equals(main, weather1.main)
                && Objects.equals(name, weather1.name)
                && Objects.equals(weather, weather1.weather);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main, name, weather, isCurrentLocation);
    }

    @NonNull
    @Override
    public String toString() {
        return "Weather{" +
                "main=" + main +
                ", name='" + name + '\'' +
                ", weather=" + weather +
                '}';
    }
}