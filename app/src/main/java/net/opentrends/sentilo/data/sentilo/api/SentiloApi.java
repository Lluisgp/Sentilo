package net.opentrends.sentilo.data.sentilo.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * Created by lgonzalez on 06/10/2017.
 */

public interface SentiloApi {

    @PUT(" ")
    Call<Void> sendLocation(@Header("IDENTITY_KEY") String token, @Body SentiloApiRequestDto sentiloApiRequestDto);
}
