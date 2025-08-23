package com.example.socialnetwork.data.source.network;


import com.example.socialnetwork.data.data_class.LoginRequest;
import com.example.socialnetwork.data.data_class.LoginResponse;
import com.example.socialnetwork.ui.auth.dataclass.User;
import com.example.socialnetwork.utils.constant.APIConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
public interface ApiService {
    @POST(APIConstant.Endpoint.LOGIN)
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST(APIConstant.Endpoint.REGISTER)
    Call<User> registerUser(@Body User user);

    // User Profile Endpoint
    @GET(APIConstant.Endpoint.USER_PROFILE)
    Call<User> getUserProfile();
}
