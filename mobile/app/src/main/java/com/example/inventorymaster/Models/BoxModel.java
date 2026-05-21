package com.example.inventorymaster.Models;

import com.example.inventorymaster.DataModels.Box;
import com.example.inventorymaster.Retrofit.Boxes.BoxResponse;
import com.example.inventorymaster.Retrofit.Boxes.BoxService;
import com.example.inventorymaster.Utils.HttpClient;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxModel {
    private BoxService service;
    private HttpClient httpClient;
    public BoxModel(HttpClient httpClient) {
        this.httpClient = httpClient;
        service = httpClient.createService(BoxService.class);
    }

    public void getBoxes(Consumer<List<Box>> handler) {
        final var call = service.getBoxes();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BoxResponse.GET> call, Response<BoxResponse.GET> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    return;
                }

                final var boxes = response.body().getBoxes();
                if(boxes.isEmpty()) {
                    httpClient.errorHandle(HttpClient.NO_RESULTS_FOUND);
                    return;
                }

                handler.accept(boxes);
            }

            @Override
            public void onFailure(Call<BoxResponse.GET> call, Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
            }
        });
    }

    public void addBox() {

    }
}
