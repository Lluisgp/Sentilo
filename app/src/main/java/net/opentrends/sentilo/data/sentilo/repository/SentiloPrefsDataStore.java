package net.opentrends.sentilo.data.sentilo.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.opentrends.sentilo.domain.models.ApplicationConfig;

public class SentiloPrefsDataStore {

    public static final String SENTILO_DATA = "sentilo.data";
    public static final String SENTILO = "sentilo";

    private final Gson gson = new Gson();

    private SharedPreferences preferences;

    public SentiloPrefsDataStore(Context context) {
        this.preferences = context.getSharedPreferences(SENTILO, Context.MODE_PRIVATE);
    }

    public ApplicationConfig getSentiloData() {
        String jsonSentilo = preferences.getString(SENTILO_DATA, gson.toJson(new ApplicationConfig()));
        ApplicationConfig applicationConfig = gson.fromJson(jsonSentilo, ApplicationConfig.class);
        return applicationConfig;
    }

    public void saveSentiloData(ApplicationConfig applicationConfig) {
        preferences.edit()
                .putString(SENTILO_DATA, gson.toJson(applicationConfig))
                .apply();
    }
}
