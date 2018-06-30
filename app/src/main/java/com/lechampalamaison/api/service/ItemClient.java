package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.api.model.apiResponse.ItemResponse;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ItemClient {

    @GET("item/filter")
    Call<ItemsResponse> itemsWithoutFilter(@Query("limit") int limit );

    @GET("item/filter")
    Call<ItemsResponse> itemsFilter(@Query("limit") int limit, @Query("manualSearch") String manualSearch , @Query("category") String category, @Query("product") String product, @Query("lat") Double lat , @Query("long") Double lng , @Query("priceMin") Double priceMin , @Query("priceMax") Double priceMax );

    @GET("item")
    Call<ItemResponse> item(@Query("idItem") int id );

}
