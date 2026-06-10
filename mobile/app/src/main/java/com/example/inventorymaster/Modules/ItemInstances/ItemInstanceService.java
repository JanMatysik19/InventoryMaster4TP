package com.example.inventorymaster.Modules.ItemInstances;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemInstanceService {
    @GET("item-instances")
    Call<ItemInstanceDTO.GetItemInstancesResponse> getItemInstances(
            @Query("page") int page,
            @Query("itemId") int itemId
    );

    @GET("item-instances")
    Call<ItemInstanceDTO.GetItemInstancesResponse> getItemInstances(
            @Query("page") int page,
            @Query("itemId") int itemId,
            @Query("sequenceNumber") int sequenceNumber
    );

    @POST("item-instances")
    Call<Void> addItemInstance(
            @Body ItemInstanceDTO.AddItemInstanceRequest request
    );

    @DELETE("item-instances/{id}")
    Call<Void> deleteItemInstance(
            @Path("id") int id
    );

    @PATCH("item-instances/{id}")
    Call<Void> moveItemInstance(
            @Path("id") int id,
            @Body ItemInstanceDTO.MoveItemInstanceRequest request
    );
}
