package com.example.inventorymaster.Utils;

import com.example.inventorymaster.DataModels.Box;
import com.example.inventorymaster.DataModels.Category;
import com.example.inventorymaster.DataModels.Item;
import com.example.inventorymaster.DataModels.ItemInstance;
import com.example.inventorymaster.Retrofit.Boxes.BoxResponse;
import com.example.inventorymaster.Retrofit.Boxes.BoxService;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    public static final String API_URL = "http://localhost:9000/";
    public static final String UNSUCCESSFUL_REQUEST = "Zapytanie do API nie powiodło się";
    public static final String NO_RESULTS_FOUND = "Brak wyników";
    public static final String OTHER_ISSUE = "Zapytanie do API nie powiodło się - sprawdź swoje połączenie internetowe";
    private Consumer<String> errorHandler;

    public final BoxService boxService;

    public HttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        boxService = retrofit.create(BoxService.class);
    }

    public void setErrorHandler(Consumer<String> handler) {
        errorHandler = handler;
    }

    public void getBoxes(Consumer<List<Box>> handler) {
        final var call = boxService.getBoxes();
        call.enqueue(new Callback<BoxResponse>() {
            @Override
            public void onResponse(Call<BoxResponse> call, Response<BoxResponse> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    if(errorHandler != null) errorHandler.accept(UNSUCCESSFUL_REQUEST);
                    return;
                }

                final var boxes = response.body().getBoxes();
                if(boxes.isEmpty()) {
                    if(errorHandler != null) errorHandler.accept(NO_RESULTS_FOUND);
                    return;
                }

                handler.accept(boxes);
            }

            @Override
            public void onFailure(Call<BoxResponse> call, Throwable t) {
                errorHandler.accept(OTHER_ISSUE);
            }
        });
    }

    public void getItemInstances(Consumer<List<ItemInstance>> handler) {
        // @TODO
    }

    public void getItems(Consumer<List<Item>> handler) {
        // @TODO
    }

    public void getCategories(Consumer<List<Category>> handler) {
        // @TODO
    }
}
