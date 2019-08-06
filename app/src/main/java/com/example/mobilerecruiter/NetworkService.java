package com.example.mobilerecruiter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    public static final String BASE_URL = "http://ec2-13-48-49-15.eu-north-1.compute.amazonaws.com:9000/";
    private Retrofit mRetrofit;
    public NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
    public server_api getJSONApi() {
        return mRetrofit.create(server_api.class);
    }
}