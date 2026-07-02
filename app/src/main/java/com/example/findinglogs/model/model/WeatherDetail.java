package com.example.findinglogs.model.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class WeatherDetail {
    private String main;
    private String description;
    private String icon;

    public WeatherDetail() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDetail that = (WeatherDetail) o;
        return Objects.equals(main, that.main)
                && Objects.equals(description, that.description)
                && Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main, description, icon);
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherDetail{" +
                "main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
