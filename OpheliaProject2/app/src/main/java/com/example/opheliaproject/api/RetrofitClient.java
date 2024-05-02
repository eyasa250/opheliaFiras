package com.example.opheliaproject.api;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "mongodb://localhost:27017/ophelia";
    private static Retrofit GsonConverterFactory;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
