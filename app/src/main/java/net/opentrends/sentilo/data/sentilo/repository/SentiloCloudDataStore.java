package net.opentrends.sentilo.data.sentilo.repository;

import android.util.Log;

import net.opentrends.sentilo.data.sentilo.api.ObservationDto;
import net.opentrends.sentilo.data.sentilo.api.SentiloApi;
import net.opentrends.sentilo.data.sentilo.api.SentiloApiRequestDto;
import net.opentrends.sentilo.domain.models.ApplicationConfig;
import net.opentrends.sentilo.domain.models.UserLocation;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.text.DateFormat;

/**
 * Created by lgonzalez on 06/10/2017.
 */

public class SentiloCloudDataStore {

    private static final String TAG = SentiloCloudDataStore.class.getName();

    public void sendLocation(ApplicationConfig applicationConfig, UserLocation userLocation) {
        Retrofit retrofit = buildRetrofit(applicationConfig);
        SentiloApi sentiloApi = retrofit.create(SentiloApi.class);

        SentiloApiRequestDto sentiloRequestDto = buildSentiloRequestDto(userLocation, applicationConfig);

        Call<Void> call = sentiloApi.sendLocation(applicationConfig.getToken(), sentiloRequestDto);
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

    private SentiloApiRequestDto buildSentiloRequestDto(UserLocation userLocation, ApplicationConfig applicationConfig) {
        SentiloApiRequestDto sentiloApiRequestDto = new SentiloApiRequestDto();

        ObservationDto observationDto = new ObservationDto();
        observationDto.setValue(applicationConfig.getNick().toString());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss'CET'");
        String ftdTime = df.format(new Date());
        observationDto.setTimestamp(ftdTime);
        //observationDto.setTimestamp("17/02/2016T11:43:45CET");
        String strLocation = String.valueOf(userLocation.getLatitude());
        strLocation +=" ";
        strLocation += String.valueOf(userLocation.getLongitude());
        observationDto.setLocation(strLocation);

        List<ObservationDto> observationDtoList = new ArrayList<>();
        observationDtoList.add(observationDto);
        sentiloApiRequestDto.setObservations(observationDtoList);
        return sentiloApiRequestDto;
    }

    private Retrofit buildRetrofit(ApplicationConfig applicationConfig) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(applicationConfig.getUrl())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
