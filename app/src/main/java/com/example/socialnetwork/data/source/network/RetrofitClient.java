package com.example.socialnetwork.data.source.network;


import com.example.socialnetwork.utils.constant.APIConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit INSTANCE;

    private RetrofitClient() {
        // Prevent instantiation
    }

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRetrofit();
                }
            }
        }
        return INSTANCE;
    }

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(APIConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(APIConstant.TimeOut.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(APIConstant.TimeOut.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(APIConstant.TimeOut.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }
}
