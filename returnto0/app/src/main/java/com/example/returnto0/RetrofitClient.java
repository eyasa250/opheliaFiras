package com.example.returnto0;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "http://10.0.2.2:4000";

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Create OkHttpClient with logging interceptor
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Set desired logging level
            httpClient.addInterceptor(logging);

            // Build Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // Add OkHttpClient to Retrofit
                    .build();
        }
        return retrofit;
    }

}
