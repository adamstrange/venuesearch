package com.strangea.venuesearch.api;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import com.strangea.venuesearch.BuildConfig;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {

    @VisibleForTesting FourSquareService service;

    private static ApiManager apiManager;

    public static void setInstance(ApiManager apiManager){
        ApiManager.apiManager = apiManager;
    }

    private ApiManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        service = retrofit.create(FourSquareService.class);
    }

    public Single<String> getVenueList(String near){
        return service.search(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, near);
    }

    public static ApiManager getApiManager() {
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }
}
