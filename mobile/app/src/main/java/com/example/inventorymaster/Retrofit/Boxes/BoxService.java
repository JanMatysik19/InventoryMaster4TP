package com.example.inventorymaster.Retrofit.Boxes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BoxService {
    @GET("boxes")
    Call<BoxResponse.GET> getBoxes();
}
