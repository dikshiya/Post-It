package com.example.postit.network;

import com.example.postit.models.commentResults;
import com.example.postit.models.postResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("app-id: 60526989411adb7ee56da94e")
    @GET("post")
    Call<postResults> getPosts(@Query("page") int page,
                               @Query("limit") int limit);

    @Headers("app-id: 60526989411adb7ee56da94e")
    @GET("post/{postId}/comment")
    Call<commentResults> getComments(@Path("postId") String postId);
}
