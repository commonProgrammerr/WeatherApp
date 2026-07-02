package com.example.findinglogs.model.repo;

import android.app.Application;

import com.example.findinglogs.model.repo.local.SharedPrefManager;
import com.example.findinglogs.model.repo.remote.WeatherManager;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class Repository implements IRepository {
    private static final String TAG = Repository.class.getSimpleName();
    private static final String LOC_COUNT_KEY = "loc_count";
    private static final String LOC_PREFIX = "loc_";

    private final WeatherManager weatherManager;
    private final SharedPrefManager sharedPrefManagerManager;

    public Repository(Application application) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "Repository()");
        weatherManager = new WeatherManager();
        sharedPrefManagerManager = SharedPrefManager.getInstance(application);
        seedDefaultCities();
    }

    private void seedDefaultCities() {
        if (sharedPrefManagerManager.readString(LOC_COUNT_KEY) != null) return;

        sharedPrefManagerManager.writeString(LOC_PREFIX + "1", "-8.05428,-34.8813");   // Recife
        sharedPrefManagerManager.writeString(LOC_PREFIX + "2", "-9.39416,-40.5096");   // Petrolina
        sharedPrefManagerManager.writeString(LOC_PREFIX + "3", "-8.284547,-35.969863"); // Caruaru
        sharedPrefManagerManager.writeString(LOC_PREFIX + "4", "-3.119027,-60.021731"); // Manaus
        sharedPrefManagerManager.writeString(LOC_PREFIX + "5", "-23.550520,-46.633308"); // São Paulo
        sharedPrefManagerManager.writeString(LOC_COUNT_KEY, "5");
    }

    public void retrieveForecast(String latLon, WeatherCallback callback) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "retrieveForecast for:" + latLon);
        weatherManager.retrieveForecast(latLon, callback);
    }

    public void saveString(String key, String value) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "saveString()");
        sharedPrefManagerManager.writeString(key, value);
    }

    public String readString(String key) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "readString()");
        return sharedPrefManagerManager.readString(key);
    }

    public Map<String, String> getLocalizations() {
        Map<String, String> localizations = new HashMap<>();
        String countStr = sharedPrefManagerManager.readString(LOC_COUNT_KEY);
        if (countStr == null) return localizations;

        int count = Integer.parseInt(countStr);
        for (int i = 1; i <= count; i++) {
            String value = sharedPrefManagerManager.readString(LOC_PREFIX + i);
            if (value != null && !localizations.containsValue(value)) {
                localizations.put(String.valueOf(i), value);
            }
        }
        return localizations;
    }

    public void addCity(String coordinates) {
        String countStr = sharedPrefManagerManager.readString(LOC_COUNT_KEY);
        int count = countStr != null ? Integer.parseInt(countStr) : 0;
        count++;
        sharedPrefManagerManager.writeString(LOC_PREFIX + count, coordinates);
        sharedPrefManagerManager.writeString(LOC_COUNT_KEY, String.valueOf(count));
    }
}
