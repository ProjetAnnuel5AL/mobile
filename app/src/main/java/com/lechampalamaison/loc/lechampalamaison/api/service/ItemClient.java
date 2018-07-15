package com.lechampalamaison.loc.lechampalamaison.api.service;

import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemPriceMinMaxResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItemClient {

    @GET("item/filter")
    Call<ItemsResponse> itemsWithoutFilter(@Query("limit") int limit);

    @GET("item/filter")
    Call<ItemsResponse> itemsFilter(@Query("limit") int limit, @Query("manualSearch") String manualSearch, @Query("category") int category, @Query("product") int product, @Query("lat") Double lat, @Query("long") Double lng, @Query("priceMin") Number priceMin, @Query("priceMax") Number priceMax);

    @GET("item")
    Call<ItemResponse> item(@Query("idItem") int id);

    @GET("item/getPriceMinMax")
    Call<ItemPriceMinMaxResponse> itemPriceMinMax();

}
