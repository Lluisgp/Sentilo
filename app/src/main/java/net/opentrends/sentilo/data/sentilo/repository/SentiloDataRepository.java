package net.opentrends.sentilo.data.sentilo.repository;

import android.content.Context;

import net.opentrends.sentilo.domain.models.ApplicationConfig;
import net.opentrends.sentilo.domain.models.UserLocation;
import net.opentrends.sentilo.domain.LocationRepository;

public class SentiloDataRepository implements LocationRepository {

    private static SentiloDataRepository INSTANCE;

    private SentiloCloudDataStore sentiloCloudDataStore;
    private SentiloPrefsDataStore sentiloPrefsDataStore;

    public static SentiloDataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            SentiloCloudDataStore sentiloCloudDataStore = new SentiloCloudDataStore();
            SentiloPrefsDataStore sentiloPrefsDataStore = new SentiloPrefsDataStore(context);
            INSTANCE = new SentiloDataRepository(sentiloCloudDataStore,sentiloPrefsDataStore);
        }
        return INSTANCE;
    }

    private SentiloDataRepository(SentiloCloudDataStore sentiloCloudDataStore, SentiloPrefsDataStore sentiloPrefsDataStore) {
        this.sentiloCloudDataStore = sentiloCloudDataStore;
        this.sentiloPrefsDataStore = sentiloPrefsDataStore;
    }

    @Override
    public void sendLocation(UserLocation userLocation) {
        ApplicationConfig applicationConfig = sentiloPrefsDataStore.getSentiloData();
        sentiloCloudDataStore.sendLocation(applicationConfig, userLocation);
    }

    @Override
    public void updateApplicationConfig(ApplicationConfig applicationConfig) {
        sentiloPrefsDataStore.saveSentiloData(applicationConfig);
    }

    @Override
    public ApplicationConfig getApplicationConfig() {
        return sentiloPrefsDataStore.getSentiloData();
    }
}
