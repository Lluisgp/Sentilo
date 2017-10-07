package net.opentrends.sentilo.data.sentilo.repository;

import android.util.Log;

import net.opentrends.sentilo.data.sentilo.api.ObservationDto;
import net.opentrends.sentilo.data.sentilo.api.SentiloApi;
import net.opentrends.sentilo.data.sentilo.api.SentiloApiRequestDto;
import net.opentrends.sentilo.domain.models.ApplicationConfig;
import net.opentrends.sentilo.domain.models.UserLocation;
import net.opentrends.sentilo.domain.utils.AplicationUtils;
import net.opentrends.sentilo.domain.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;

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

    public void sendLocation(ApplicationConfig applicationConfig, UserLocation userLocation) {
        try {
            Retrofit retrofit = buildRetrofit(applicationConfig);
            SentiloApi sentiloApi = retrofit.create(SentiloApi.class);

            SentiloApiRequestDto sentiloRequestDto = buildSentiloRequestDto(userLocation, applicationConfig);

            Call<Void> call = sentiloApi.sendLocation(applicationConfig.getToken(), sentiloRequestDto);
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, response.toString());
                    EventBus.getDefault().post(new MessageEvent(response.toString()));

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d(TAG, "Error " + t.getMessage());
                    EventBus.getDefault().post(new MessageEvent(TAG + " Error " + t.getMessage()));
                }
            });
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(TAG + " Error Catch " + e.getMessage()));
        }
    }

    private SentiloApiRequestDto buildSentiloRequestDto(UserLocation userLocation, ApplicationConfig applicationConfig) {

        SentiloApiRequestDto sentiloApiRequestDto = new SentiloApiRequestDto();
        AplicationUtils aplicationUtil = new AplicationUtils();

        ObservationDto observationDto = new ObservationDto();
        observationDto.setValue(applicationConfig.getNick().toString());
        observationDto.setTimestamp(aplicationUtil.getFormatedTimeStamp());
        observationDto.setLocation(aplicationUtil.getLocationString(userLocation));

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
