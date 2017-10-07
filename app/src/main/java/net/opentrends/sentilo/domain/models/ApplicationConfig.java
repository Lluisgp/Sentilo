package net.opentrends.sentilo.domain.models;

/**
 * Created by lgonzalez on 06/10/2017.
 */

public class ApplicationConfig {

    private String token;

    private String url;

    private String nick;

    private int secondsBetweenLocationUpdates;

    private boolean enableLocation;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getSecondsBetweenLocationUpdates() {
        return secondsBetweenLocationUpdates;
    }

    public void setSecondsBetweenLocationUpdates(int secondsBetweenLocationUpdates) {
        this.secondsBetweenLocationUpdates = secondsBetweenLocationUpdates;
    }

    public boolean isEnableLocation() {
        return enableLocation;
    }

    public void setEnableLocation(boolean enableLocation) {
        this.enableLocation = enableLocation;
    }
}
