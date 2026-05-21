package com.example.inventorymaster.Retrofit.Boxes;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BoxService {
    @GET("boxes")
    Call<BoxResponse.Many> getBoxes();
    @GET("boxes/{id}")
    Call<BoxResponse.One> getBox(
            @Path("id") int id
    );
    @FormUrlEncoded
    @POST("boxes")
    Call<Void> addBox();
    @DELETE("boxes/{id}")
    Call<Void> deleteBox(
            @Path("id") int id
    );
}
