package net.opentrends.sentilo.domain.utils;

import net.opentrends.sentilo.domain.models.UserLocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lgonzalez on 07/10/2017.
 */

public class AplicationUtils {

    /**
     * Return actual date/time in correct format for the api
     *
     * @return date/time in timestamp string
     */
    public String getFormatedTimeStamp() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss'CET'");
        return df.format(new Date());
    }

    /**
     * With userLocation parameter, make and String response in format
     * for the api.
     *
     * @param userLocation
     * @return
     */
    public String getLocationString(UserLocation userLocation) {
        String strLocation = String.valueOf(userLocation.getLatitude());
        strLocation += " ";
        strLocation += String.valueOf(userLocation.getLongitude());
        return strLocation;
    }
}
