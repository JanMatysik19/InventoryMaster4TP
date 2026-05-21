package com.example.inventorymaster.Retrofit.Categories;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories")
    Call<CategoryResponse.GET> getCategories();
    @FormUrlEncoded
    @POST("categories")
    Call<Void> addCategory(
            @Field("code") String code
    );
    @DELETE("categories/{id}")
    Call<Void> deleteCategory(
            @Path("id") int id
    );
}
