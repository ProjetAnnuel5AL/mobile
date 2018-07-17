package com.lechampalamaison.loc.lechampalamaison.api.service;

import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProducerClient {



    @GET("getPublicInformations")
    Call<ProducerResponse> producerGetPublicInformations(@Query("idProducer") int id);

}
