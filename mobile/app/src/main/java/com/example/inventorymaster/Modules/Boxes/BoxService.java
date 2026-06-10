package com.example.inventorymaster.Modules.Boxes;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoxService {
    @GET("boxes")
    Call<BoxDTO.GetBoxesResponse> getBoxes(
            @Query("page") int page
    );

    @GET("boxes")
    Call<BoxDTO.GetBoxesResponse> getBoxes(
            @Query("page") int page,
            @Query("sequenceNumber") int sequenceNumber
    );

    @GET("boxes/{id}")
    Call<BoxDTO.GetBoxResponse> getBox(
            @Path("id") int id
    );

    @POST("boxes")
    Call<Void> addBox();

    @DELETE("boxes/{id}")
    Call<Void> deleteBox(
            @Path("id") int id
    );

}
