package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.AuthResponse;
import com.lechampalamaison.api.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("user/auth")
    Call<AuthResponse> login(@Body Login login);
}
