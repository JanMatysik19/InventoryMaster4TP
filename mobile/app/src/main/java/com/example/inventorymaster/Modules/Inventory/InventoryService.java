package com.example.inventorymaster.Modules.Inventory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InventoryService {
    @GET("inventory?view=boxes")
    Call<InventoryDTO.GetTotalBoxesResponse> getTotalBoxes();


    @GET("inventory?view=boxes")
    Call<InventoryDTO.GetTotalBoxesResponse> getTotalBoxes(
            @Query("sequenceNumber") int sequenceNumber
    );


    @GET("inventory?view=items")
    Call<InventoryDTO.GetTotalItemsResponse> getTotalItems();


    @GET("inventory?view=items")
    Call<InventoryDTO.GetTotalItemsResponse> getTotalItems(
            @Query("search") String search
    );

    @GET("inventory?view=item-instances")
    Call<InventoryDTO.GetTotalItemInstancesResponse> getTotalItemInstances(
            @Query("itemId") int itemId
    );

    @GET("inventory?view=item-instances")
    Call<InventoryDTO.GetTotalItemInstancesResponse> getTotalItemInstances(
            @Query("itemId") int itemId,
            @Query("sequenceNumber") int sequenceNumber
    );

    @GET("inventory?view=summary")
    Call<InventoryDTO.GetSummaryResponse> getSummary();
}
