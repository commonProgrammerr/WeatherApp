package com.example.findinglogs.model.repo;

import android.app.Application;

import com.example.findinglogs.model.repo.local.SharedPrefManager;
import com.example.findinglogs.model.repo.remote.WeatherManager;
import com.example.findinglogs.model.repo.remote.api.GeoCallback;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class Repository implements IRepository {
    private static final String TAG = Repository.class.getSimpleName();
    private static final String LOC_COUNT_KEY = "loc_count";
    private static final String LOC_PREFIX = "loc_";
    private static final String CITY_NAME_PREFIX = "city_name_";

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

        writeCity(1, "Recife", "-8.05428,-34.8813");
        writeCity(2, "Petrolina", "-9.39416,-40.5096");
        writeCity(3, "Caruaru", "-8.284547,-35.969863");
        writeCity(4, "Manaus", "-3.119027,-60.021731");
        writeCity(5, "São Paulo", "-23.550520,-46.633308");
        sharedPrefManagerManager.writeString(LOC_COUNT_KEY, "5");
    }

    private void writeCity(int index, String name, String coordinates) {
        String key = String.valueOf(index);
        sharedPrefManagerManager.writeString(LOC_PREFIX + key, coordinates);
        sharedPrefManagerManager.writeString(CITY_NAME_PREFIX + key, name);
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
        Map<String, String> localizations = new LinkedHashMap<>();
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

    public String getCityName(String key) {
        return sharedPrefManagerManager.readString(CITY_NAME_PREFIX + key);
    }

    public void addCity(String name, String coordinates) {
        String countStr = sharedPrefManagerManager.readString(LOC_COUNT_KEY);
        int count = countStr != null ? Integer.parseInt(countStr) : 0;
        count++;
        String key = String.valueOf(count);
        sharedPrefManagerManager.writeString(LOC_PREFIX + key, coordinates);
        sharedPrefManagerManager.writeString(CITY_NAME_PREFIX + key, name);
        sharedPrefManagerManager.writeString(LOC_COUNT_KEY, String.valueOf(count));
    }

    public void removeCity(String key) {
        String countStr = sharedPrefManagerManager.readString(LOC_COUNT_KEY);
        if (countStr == null) return;

        int count = Integer.parseInt(countStr);
        int keyInt = Integer.parseInt(key);

        // Shift subsequent entries forward
        for (int i = keyInt + 1; i <= count; i++) {
            String value = sharedPrefManagerManager.readString(LOC_PREFIX + i);
            String name = sharedPrefManagerManager.readString(CITY_NAME_PREFIX + i);
            if (value != null) {
                sharedPrefManagerManager.writeString(LOC_PREFIX + (i - 1), value);
                sharedPrefManagerManager.writeString(CITY_NAME_PREFIX + (i - 1), name);
            }
        }

        sharedPrefManagerManager.writeString(LOC_COUNT_KEY, String.valueOf(count - 1));
    }

    public void searchCities(String query, GeoCallback callback) {
        weatherManager.searchCities(query, callback);
    }
}
