package com.example.findinglogs.model.repo.remote.api;

import com.example.findinglogs.model.model.GeoCity;

import java.util.List;

public interface GeoCallback {
    void onSuccess(List<GeoCity> results);
    void onFailure(String msg);
}
