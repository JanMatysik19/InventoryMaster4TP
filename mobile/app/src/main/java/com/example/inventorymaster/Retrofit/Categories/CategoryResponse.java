package com.example.inventorymaster.Retrofit.Categories;

import com.example.inventorymaster.DataModels.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    public static class GET {
        @SerializedName("items")
        private List<Item> items;

        public List<Category> getCategories() {
            final List<Category> categories = new ArrayList<>();
            if (items == null) return categories;

            for(var item : items) categories.add(new Category(item.id, item.code));
            return categories;
        }

        public static class Item {
            @SerializedName("id")
            public int id;
            @SerializedName("code")
            public String code;
        }
    }
}
