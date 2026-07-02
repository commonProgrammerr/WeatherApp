package com.example.findinglogs.viewmodel;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.repo.IRepository;
import com.example.findinglogs.model.repo.Repository;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final int FETCH_INTERVAL = 120_000;
    private final IRepository mRepository;
    private final MutableLiveData<List<Weather>> _weatherList = new MutableLiveData<>(new ArrayList<>());
    private final LiveData<List<Weather>> weatherList = _weatherList;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable fetchRunnable = this::fetchAllForecasts;
    private boolean isFetching = false;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        startFetching();
    }

    // Constructor for testing — allows injecting a mock IRepository
    MainViewModel(Application application, IRepository repository) {
        super(application);
        mRepository = repository;
        startFetching();
    }

    public LiveData<List<Weather>> getWeatherList() {
        return weatherList;
    }

    private void startFetching() {
        fetchAllForecasts();
    }

    private void fetchAllForecasts() {
        if (isFetching) {
            if (Logger.ISLOGABLE) Logger.d(TAG, "fetch already in progress, skipping");
            return;
        }

        if (Logger.ISLOGABLE) Logger.d(TAG, "fetchAllForecasts()");
        isFetching = true;
        Map<String, String> localizations = mRepository.getLocalizations();
        List<Weather> updatedList = new ArrayList<>();
        int[] completedCount = {0};

        for (String latlon : localizations.values()) {
            mRepository.retrieveForecast(latlon, new WeatherCallback() {
                @Override
                public void onSuccess(Weather result) {
                    synchronized (completedCount) {
                        updatedList.add(result);
                        completedCount[0]++;
                        if (completedCount[0] == localizations.size()) {
                            _weatherList.setValue(new ArrayList<>(updatedList));
                            isFetching = false;
                            handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
                        }
                    }
                }

                @Override
                public void onFailure(String error) {
                    synchronized (completedCount) {
                        completedCount[0]++;
                        if (completedCount[0] == localizations.size()) {
                            isFetching = false;
                            handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        handler.removeCallbacks(fetchRunnable);
        super.onCleared();
    }

    public void retrieveForecast(String latLon, WeatherCallback callback) {
        mRepository.retrieveForecast(latLon, callback);
    }

    public void refresh() {
        fetchAllForecasts();
    }
}