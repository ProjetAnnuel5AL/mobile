package com.lechampalamaison.loc.lechampalamaison.api.service;

import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductClient {


    @GET("products/findByCategoryId")
    Call<ProductResponse> productsFindByCategoryId(@Query("id") int id);

    @GET("products")
    Call<ProductResponse> products();

}
