package com.lechampalamaison.api.service;

import com.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ItemClient {
    @GET("item/filter")
    Call<ItemsResponse> itemsWithouFilter(@Query("limit") int limit );

}
