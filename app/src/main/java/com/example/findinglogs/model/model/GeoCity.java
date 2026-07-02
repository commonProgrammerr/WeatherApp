package com.example.findinglogs.model.model;

import com.google.gson.annotations.SerializedName;

public class GeoCity {
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getFormattedName() {
        if (state != null && !state.isEmpty()) {
            return name + ", " + state + ", " + country;
        }
        return name + ", " + country;
    }

    public String getCoordinates() {
        return lat + "," + lon;
    }
}
