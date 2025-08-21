package com.example.socialnetwork.data.source.network;

import com.example.socialnetwork.data.model.dto.BaseResponse;
import com.example.socialnetwork.data.model.dto.LoginRequest;
import com.example.socialnetwork.data.model.dto.LoginResponse;
import com.example.socialnetwork.data.model.dto.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("/api/v1/auth/login")
    Call<BaseResponse<LoginResponse>> login(@Body LoginRequest loginRequest);
    @POST("/api/v1/auth/register")
    Call<BaseResponse<Object>> register(@Body SignUpRequest signUpRequest);
}