package com.example.inventorymaster.Models;

import com.example.inventorymaster.DataModels.Category;
import com.example.inventorymaster.Retrofit.Categories.CategoryResponse;
import com.example.inventorymaster.Retrofit.Categories.CategoryService;
import com.example.inventorymaster.Utils.HttpClient;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryModel {
    private CategoryService service;
    private HttpClient httpClient;
    public CategoryModel(HttpClient httpClient) {
        this.httpClient = httpClient;
        service = httpClient.createService(CategoryService.class);
    }

    public void getCategories(Consumer<List<Category>> handler) {
        final var call = service.getCategories();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CategoryResponse.GET> call, Response<CategoryResponse.GET> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    return;
                }

                final var categories = response.body().getCategories();
                if(categories.isEmpty()) {
                    httpClient.errorHandle(HttpClient.NO_RESULTS_FOUND);
                    return;
                }

                handler.accept(categories);
            }

            @Override
            public void onFailure(Call<CategoryResponse.GET> call, Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
            }
        });
    }

    public void addCategory(String code, Consumer<Void> handler) {
        final var call = service.addCategory(code);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
            }
        });
    }

    public void deleteCategory(int id, Consumer<Void> handler) {
        final var call = service.deleteCategory(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClient.errorHandle(HttpClient.UNSUCCESSFUL_REQUEST);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                httpClient.errorHandle(HttpClient.OTHER_ISSUE);
            }
        });
    }
}
