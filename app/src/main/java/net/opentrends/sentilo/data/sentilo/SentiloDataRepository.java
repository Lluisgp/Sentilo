package net.opentrends.sentilo.data.sentilo;

import android.content.Context;

import net.opentrends.sentilo.data.models.SentiloConfig;
import net.opentrends.sentilo.data.models.UserLocation;

public class SentiloDataRepository implements SentiloRepository {

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
        SentiloConfig sentiloConfig = sentiloPrefsDataStore.getSentiloData();
        sentiloCloudDataStore.sendLocation(sentiloConfig, userLocation);
    }

    @Override
    public void updateSentiloData(SentiloConfig sentiloConfig) {
        sentiloPrefsDataStore.saveSentiloData(sentiloConfig);
    }

    @Override
    public SentiloConfig getSentiloData() {
        return sentiloPrefsDataStore.getSentiloData();
    }
}
