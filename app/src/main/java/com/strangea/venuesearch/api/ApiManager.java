package com.strangea.venuesearch.api;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import com.strangea.venuesearch.BuildConfig;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
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

    public @Inject ApiManager(FourSquareService squareService){
        this.service = squareService;
    }

    public Single<String> getVenueList(String near){
        return service.search(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, near);
    }
}
