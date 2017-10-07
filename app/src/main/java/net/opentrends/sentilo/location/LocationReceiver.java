package net.opentrends.sentilo.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import net.opentrends.sentilo.data.sentilo.repository.SentiloDataRepository;
import net.opentrends.sentilo.domain.models.UserLocation;
import net.opentrends.sentilo.domain.LocationRepository;

import br.com.safety.locationlistenerhelper.core.SettingsLocationTracker;

/**
 * Created by lgonzalez on 06/10/2017.
 */

public class LocationReceiver extends BroadcastReceiver {

    private static final String TAG = LocationReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent && intent.getAction().equals("sentilo.location")) {
            Location locationData = (Location) intent.getParcelableExtra(SettingsLocationTracker.LOCATION_MESSAGE);
            Log.d(TAG, "Latitude: " + locationData.getLatitude() + "Longitude:" + locationData.getLongitude());
            //send your call to api or do any things with the of location data

            UserLocation userLocation = new UserLocation();
            userLocation.setLatitude(locationData.getLatitude());
            userLocation.setLongitude(locationData.getLongitude());

            LocationRepository locationRepository = SentiloDataRepository.getInstance(context);
            locationRepository.sendLocation(userLocation);
        }
    }
}
