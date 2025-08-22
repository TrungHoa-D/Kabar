package com.example.socialnetwork.data.source.network;

import android.content.Context;

import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.utils.constant.APIConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit INSTANCE;

    private RetrofitClient() {
    }

    public static Retrofit getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRetrofit(context);
                }
            }
        }
        return INSTANCE;
    }


    private static Retrofit buildRetrofit(Context context) {
        return new Retrofit.Builder()
                .baseUrl(APIConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(context))
                .build();
    }


    private static OkHttpClient provideOkHttpClient(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        TokenManager tokenManager = new TokenManager(context.getApplicationContext());

        return new OkHttpClient.Builder()
                .connectTimeout(APIConstant.TimeOut.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(APIConstant.TimeOut.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(APIConstant.TimeOut.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

                .addInterceptor(new AuthInterceptor(tokenManager))
                .build();
    }
}