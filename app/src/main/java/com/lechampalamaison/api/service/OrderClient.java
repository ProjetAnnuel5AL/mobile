package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.Order;
import com.lechampalamaison.api.model.apiResponse.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderClient {

    @POST("order")
    Call<OrderResponse> saveOrder(@Body Order order);
}
