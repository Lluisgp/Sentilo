package net.opentrends.sentilo.data.sentilo;

import android.util.Log;

import net.opentrends.sentilo.data.api.ObservationDto;
import net.opentrends.sentilo.data.api.SentiloApi;
import net.opentrends.sentilo.data.api.SentiloApiRequestDto;
import net.opentrends.sentilo.data.models.SentiloConfig;
import net.opentrends.sentilo.data.models.UserLocation;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lgonzalez on 06/10/2017.
 */

public class SentiloCloudDataStore {

    private static final String TAG = SentiloCloudDataStore.class.getName();

    public void sendLocation(SentiloConfig sentiloConfig, UserLocation userLocation) {
        Retrofit retrofit = buildRetrofit(sentiloConfig);
        SentiloApi sentiloApi = retrofit.create(SentiloApi.class);

        SentiloApiRequestDto sentiloRequestDto = buildSentiloRequestDto(userLocation);

        Call<Void> call = sentiloApi.sendLocation(sentiloConfig.getToken(), sentiloRequestDto);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "error " + t.getMessage());
            }
        });
    }

    private SentiloApiRequestDto buildSentiloRequestDto(UserLocation userLocation) {
        SentiloApiRequestDto sentiloApiRequestDto = new SentiloApiRequestDto();
        ObservationDto observationDto = new ObservationDto();
        observationDto.setValue("1");
        observationDto.setTimestamp("17/02/2016T11:43:45CET");
        String strLocation = String.valueOf(userLocation.getLatitude());
        strLocation +=" ";
        strLocation += String.valueOf(userLocation.getLongitude());
        observationDto.setLocation(strLocation);

        List<ObservationDto> observationDtoList = new ArrayList<>();
        observationDtoList.add(observationDto);
        sentiloApiRequestDto.setObservations(observationDtoList);
        return sentiloApiRequestDto;
    }

    private Retrofit buildRetrofit(SentiloConfig sentiloConfig) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sentiloConfig.getUrl())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
