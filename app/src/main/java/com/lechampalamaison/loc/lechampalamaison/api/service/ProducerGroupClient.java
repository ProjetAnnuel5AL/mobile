package com.lechampalamaison.loc.lechampalamaison.api.service;


import com.lechampalamaison.loc.lechampalamaison.api.model.Subscribe;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CheckFounderResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CollectiviteResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.EventResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupEventParticiantResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupEventResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupMemberResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupSubscriberCheckResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.SubscribeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProducerGroupClient {

    @GET("producersGroup/search")
    Call<ProducerGroupResponse> producersGroup(@Query("lat") Double lat, @Query("long") Double lng);

    @GET("producersGroup/idGroup/")
    Call<CollectiviteResponse> producersGroupIdGroup(@Query("idGroup") int idGroup);

    @GET("producersGroupMember/idGroup/")
    Call<ProducerGroupMemberResponse> producersGroupMember(@Query("idGroup") int idGroup);

    @GET("producersGroupEvent/idGroup/")
    Call<ProducerGroupEventResponse> producersGroupEvent(@Query("idGroup") int idGroup);

    @GET("producersGroupMember/idUser/idGroup")
    Call<SubscribeResponse> producerGroupMemberCheck(@Query("idGroup") int idGroup, @Query("token") String token);

    @GET("producersGroup/founder/userId/")
    Call<CheckFounderResponse> producerGroupFounderCheck(@Query("loginUser") String loginUser, @Query("token") String token);

    @GET("producersGroupSubscriber/idUser/idGroup")
    Call<ProducerGroupSubscriberCheckResponse> producerGroupSubscriberCheck(@Query("idGroup") int idGroup, @Query("token") String token);

    @POST("producersGroupSubscriber")
    Call<SubscribeResponse> producersGroupSubscriber(@Body Subscribe subscribe);

    @HTTP(method = "DELETE", path = "producersGroupSubscriber/idUser/idGroup", hasBody = true)
    Call<SubscribeResponse> producersGroupUnsubscriber(@Body Subscribe subscribe);

    @GET("producersGroupEventParticipant/producer/idEvent/")
    Call<ProducerGroupEventParticiantResponse> producersGroupEventParticipant(@Query("idEvent") int idEvent, @Query("token") String token);

    @GET("producersGroupEvent/id/")
    Call<EventResponse> event(@Query("idEvent") int idEvent, @Query("token") String token);
}
