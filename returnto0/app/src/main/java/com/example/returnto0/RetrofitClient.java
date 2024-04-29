package com.example.returnto0;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:4000";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder();
                        builder.header("Authorization", "Bearer " +  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2MmUwOTE3NmZiMDg1MDE1NmU5ZjYwNiIsInJvbGUiOiJtZXJlIiwiaWF0IjoxNzE0NDIzNzA5LCJleHAiOjE3MjIxOTk3MDl9.TTFaBG1H7kIFAM70zGkMxCVZqQRl7b1WNw_rpCTBEW8");

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
