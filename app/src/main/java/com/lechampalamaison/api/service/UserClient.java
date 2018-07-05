package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.Update;
import com.lechampalamaison.api.model.apiResponse.SignupResponse;
import com.lechampalamaison.api.model.apiResponse.AuthResponse;
import com.lechampalamaison.api.model.Login;
import com.lechampalamaison.api.model.Signup;
import com.lechampalamaison.api.model.apiResponse.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("user/auth")
    Call<AuthResponse> login(@Body Login login);

    @POST("user")
    Call<SignupResponse> signup(@Body Signup signup);

    @POST("user/update")
    Call<UpdateResponse> update(@Body Update update);
}
