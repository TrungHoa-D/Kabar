package com.example.socialnetwork.data.source.network;

import android.content.Context;

public class ApiUtils {
    public static ApiService getApiService(Context context) {
        return RetrofitClient.getInstance(context).create(ApiService.class);
    }

    public static AuthApiService getAuthApiService(Context context) {
        return RetrofitClient.getInstance(context).create(AuthApiService.class);
    }
}