package com.example.findinglogs.model.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class WeatherInfo {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float pressure;
    private float humidity;

    public WeatherInfo() {
    }

    public WeatherInfo(float temp, float feels_like, float temp_min, float temp_max, float pressure, float humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInfo that = (WeatherInfo) o;
        return Float.compare(that.temp, temp) == 0
                && Float.compare(that.feels_like, feels_like) == 0
                && Float.compare(that.temp_min, temp_min) == 0
                && Float.compare(that.temp_max, temp_max) == 0
                && Float.compare(that.pressure, pressure) == 0
                && Float.compare(that.humidity, humidity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temp, feels_like, temp_min, temp_max, pressure, humidity);
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temp=" + temp +
                ", feels_like=" + feels_like +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}