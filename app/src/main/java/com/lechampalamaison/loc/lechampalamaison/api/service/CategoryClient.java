package com.lechampalamaison.loc.lechampalamaison.api.service;


import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryClient {

    @GET("categories")
    Call<CategoryResponse> category();


}
