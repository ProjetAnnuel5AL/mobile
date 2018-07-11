package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.apiResponse.FindOrdersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderClient {


    @GET("order/getOdrersFromUser")
    Call<FindOrdersResponse> findOrders(@Query("loginUser") String loginUser, @Query("token") String token );


}
