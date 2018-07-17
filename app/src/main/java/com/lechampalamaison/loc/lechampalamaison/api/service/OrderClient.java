package com.lechampalamaison.loc.lechampalamaison.api.service;


import com.lechampalamaison.loc.lechampalamaison.api.model.Order;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindOrderLignesResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindOrdersResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderClient {


    @GET("order/getOdrersFromUser")
    Call<FindOrdersResponse> findOrders(@Query("loginUser") String loginUser, @Query("token") String token);

    @GET("order/getOrderDetailsFromUser")
    Call<FindOrderLignesResponse> findOrderLignes(@Query("loginUser") String loginUser, @Query("token") String token, @Query("idOrder") int idOrder);

    @POST("order")
    Call<OrderResponse> saveOrder(@Body Order order);
}
