package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.User;
import com.lechampalamaison.api.model.apiResponse.FindAddressReponse;
import com.lechampalamaison.api.model.apiResponse.FindEmailResponse;
import com.lechampalamaison.api.model.apiResponse.SignupResponse;
import com.lechampalamaison.api.model.apiResponse.AuthResponse;
import com.lechampalamaison.api.model.Login;
import com.lechampalamaison.api.model.Signup;
import com.lechampalamaison.api.model.apiResponse.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserClient {

    @POST("user/auth")
    Call<AuthResponse> login(@Body Login login);

    @POST("user")
    Call<SignupResponse> signup(@Body Signup signup);

    @POST("user/update")
    Call<UpdateResponse> update(@Body User user);

    @GET("user/findEmail")
    Call<FindEmailResponse> findEmail(@Query("loginUser") String loginUser, @Query("token") String token );

    @GET("/user/findAddress")
    Call<FindAddressReponse> findAddress(@Query("loginUser") String loginUser, @Query("token") String token );

}
