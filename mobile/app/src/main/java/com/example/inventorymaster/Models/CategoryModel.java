package com.example.inventorymaster.Models;

import com.example.inventorymaster.DataModels.Category;
import com.example.inventorymaster.Retrofit.Categories.CategoryResponse;
import com.example.inventorymaster.Retrofit.Categories.CategoryService;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryModel {
    private CategoryService service;
    private HttpClientModel httpClientModel;
    public CategoryModel(HttpClientModel httpClientModel) {
        this.httpClientModel = httpClientModel;
        service = httpClientModel.createService(CategoryService.class);
    }

    public void getCategories(Consumer<List<Category>> handler) {
        final var call = service.getCategories();
        call.enqueue(new Callback<CategoryResponse.GET>() {
            @Override
            public void onResponse(Call<CategoryResponse.GET> call, Response<CategoryResponse.GET> response) {
                android.util.Log.d("CATEGORY MODEL", "Response code: " + response.code());
                if(!response.isSuccessful() || response.body() == null) {
                    httpClientModel.errorHandle(HttpClientModel.UNSUCCESSFUL_REQUEST);
                    return;
                }

                final var categories = response.body().getCategories();
                android.util.Log.d("CATEGORY MODEL", "Parsed " + categories.size() + " categories");
                if(categories.isEmpty()) {
                    httpClientModel.errorHandle(HttpClientModel.NO_RESULTS_FOUND);
                    return;
                }

                handler.accept(categories);
            }

            @Override
            public void onFailure(Call<CategoryResponse.GET> call, Throwable t) {
                android.util.Log.e("CATEGORY MODEL", "Network error", t);
                httpClientModel.errorHandle(HttpClientModel.OTHER_ISSUE + " (" + t.getMessage() + ")");
            }
        });
    }

    public void addCategory(String code, Consumer<Void> handler) {
        final var call = service.addCategory(code);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClientModel.errorHandle(HttpClientModel.UNSUCCESSFUL_REQUEST);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                httpClientModel.errorHandle(HttpClientModel.OTHER_ISSUE);
            }
        });
    }

    public void deleteCategory(int id, Consumer<Void> handler) {
        final var call = service.deleteCategory(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    httpClientModel.errorHandle(HttpClientModel.UNSUCCESSFUL_REQUEST);
                    return;
                }

                handler.accept(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                httpClientModel.errorHandle(HttpClientModel.OTHER_ISSUE);
            }
        });
    }
}
