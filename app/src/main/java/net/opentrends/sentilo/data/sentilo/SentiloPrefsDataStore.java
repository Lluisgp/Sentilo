package net.opentrends.sentilo.data.sentilo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.opentrends.sentilo.data.models.SentiloConfig;

public class SentiloPrefsDataStore {

    public static final String SENTILO_DATA = "sentilo.data";
    public static final String SENTILO = "sentilo";

    private final Gson gson = new Gson();

    private SharedPreferences preferences;

    public SentiloPrefsDataStore(Context context) {
        this.preferences = context.getSharedPreferences(SENTILO, Context.MODE_PRIVATE);
    }

    public SentiloConfig getSentiloData() {
        String jsonSentilo = preferences.getString(SENTILO_DATA, gson.toJson(new SentiloConfig()));
        SentiloConfig sentiloConfig = gson.fromJson(jsonSentilo, SentiloConfig.class);
        return sentiloConfig;
    }

    public void saveSentiloData(SentiloConfig sentiloConfig) {
        preferences.edit()
                .putString(SENTILO_DATA, gson.toJson(sentiloConfig))
                .apply();
    }
}
