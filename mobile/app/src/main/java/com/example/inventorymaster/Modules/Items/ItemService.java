package com.example.inventorymaster.Modules.Items;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {
    @GET("items")
    Call<ItemDTO.GetItemsResponse> getItems(
            @Query("page") int page
    );

    @GET("items")
    Call<ItemDTO.GetItemsResponse> getItems(
            @Query("page") int page,
            @Query("search") String search
    );

    @GET("items/{id}")
    Call<ItemDTO.GetItemResponse> getItem(
            @Path("id") int id
    );

    @POST("items")
    Call<Void> addItem(
            @Body ItemDTO.AddItemRequest request
    );

    @DELETE("items/{id}")
    Call<Void> deleteItem(
            @Path("id") int id
    );

    @PUT("items/{id}")
    Call<Void> updateItem(
            @Path("id") int id,
            @Body ItemDTO.UpdateItemRequest request
    );
}
