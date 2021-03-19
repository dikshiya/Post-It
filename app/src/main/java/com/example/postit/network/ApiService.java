package com.example.postit.network;

import com.example.postit.models.typeResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("app-id: 60526989411adb7ee56da94e")
    @GET("post")
    Call<typeResults> getPosts(@Query("app-id") String appId);

    @Headers("app-id : 60526989411adb7ee56da94e")
    @GET("post/{postId}/comment")
    Call<typeResults> getComments(@Query("app-id") String appId,
                       @Query("postId") String postId);
}
