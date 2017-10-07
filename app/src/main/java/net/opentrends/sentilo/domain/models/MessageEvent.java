package net.opentrends.sentilo.domain.models;

/**
 * Created by lgonzalez on 07/10/2017.
 */

public class MessageEvent {

    String message;


    public MessageEvent(String str) {
        this.message = str;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
