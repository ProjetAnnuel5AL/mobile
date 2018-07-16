package com.lechampalamaison.loc.lechampalamaison.api.service;

import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.api.model.Login;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.AuthResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemPriceMinMaxResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.QuantityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("item/verifyQuantityItems")
    Call<QuantityResponse> itemQuantity(@Body List<CartItem> items);



}
