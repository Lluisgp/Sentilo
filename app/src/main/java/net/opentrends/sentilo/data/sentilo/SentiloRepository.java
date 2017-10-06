package net.opentrends.sentilo.data.sentilo;

import net.opentrends.sentilo.data.models.SentiloConfig;
import net.opentrends.sentilo.data.models.UserLocation;

public interface SentiloRepository {
    void sendLocation(UserLocation userLocation);
    void updateSentiloData(SentiloConfig sentiloConfig);
    SentiloConfig getSentiloData();
}
