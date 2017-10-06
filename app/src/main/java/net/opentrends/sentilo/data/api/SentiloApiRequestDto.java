package net.opentrends.sentilo.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgonzalez on 07/10/2017.
 */

public class SentiloApiRequestDto {

    @SerializedName("observations")
    private List<ObservationDto> observations = null;

    public List<ObservationDto> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationDto> observations) {
        this.observations = observations;
    }
}
