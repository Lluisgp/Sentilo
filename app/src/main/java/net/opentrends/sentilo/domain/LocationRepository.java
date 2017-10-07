package net.opentrends.sentilo.domain;

import net.opentrends.sentilo.domain.models.ApplicationConfig;
import net.opentrends.sentilo.domain.models.UserLocation;

public interface LocationRepository {
    void sendLocation(UserLocation userLocation);
    void updateApplicationConfig(ApplicationConfig applicationConfig);
    ApplicationConfig getApplicationConfig();
}
