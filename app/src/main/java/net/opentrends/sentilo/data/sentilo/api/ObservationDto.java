package net.opentrends.sentilo.data.sentilo.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgonzalez on 07/10/2017.
 */

public class ObservationDto {

    @SerializedName("value")
    private String value;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("location")
    private String location;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}