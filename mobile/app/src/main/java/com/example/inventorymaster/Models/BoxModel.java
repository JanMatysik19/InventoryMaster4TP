package com.example.inventorymaster.Models;

import androidx.annotation.NonNull;

import com.example.inventorymaster.DataModels.Box;
import com.example.inventorymaster.Retrofit.Boxes.BoxResponse;
import com.example.inventorymaster.Retrofit.Boxes.BoxService;
import com.example.inventorymaster.Utils.HttpClient;

import org.jspecify.annotations.Nullable;

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

    public void getBoxes(Consumer<List<@Nullable Box>> handler) {
        final var call = service.getBoxes();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BoxResponse.Many> call, @NonNull Response<BoxResponse.Many> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    handler.accept(null);
                    return;
                }

                final var result = response.body().getBoxes();
                if(result.isEmpty()) {
                    httpClient.errorHandle(HttpClient.NO_RESULTS_FOUND);
                    return;
                }

                handler.accept(result);
            }

            @Override
            public void onFailure(@NonNull Call<BoxResponse.Many> call, @NonNull Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
                handler.accept(null);
            }
        });
    }

    public void getBox(int id, Consumer<@Nullable Box> handler) {
        final var call = service.getBox(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BoxResponse.One> call, @NonNull Response<BoxResponse.One> response) {
                if(response.code() == 404) { // Not Found
                    httpClient.errorHandle(HttpClient.NO_RESULTS_FOUND);
                    handler.accept(null);
                    return;
                }

                if(!response.isSuccessful() || response.body() == null) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    handler.accept(null);
                    return;
                }

                final var result = response.body().getBox();

                handler.accept(result);
            }

            @Override
            public void onFailure(@NonNull Call<BoxResponse.One> call, @NonNull Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
                handler.accept(null);
            }
        });
    }

    public void deleteBox(int id, Consumer<Void> handler) {
        final var call = service.deleteBox(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    handler.accept(null);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
                handler.accept(null);
            }
        });
    }
    
    public void addBox(Consumer<Void> handler) {
        final var call = service.addBox();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    handler.accept(null);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
                handler.accept(null);
            }
        });
    }
}
