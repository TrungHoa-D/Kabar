package com.example.socialnetwork.data.source.network;

public class ApiUtils {
    public static AuthApiService getAuthApiService() {
        return RetrofitClient.getInstance().create(AuthApiService.class);
    }
}