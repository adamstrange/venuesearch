package com.strangea.venuesearch.api;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FourSquareService {
    @GET("search?v=20180706")
    Single<String> search(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("near") String near );
}
