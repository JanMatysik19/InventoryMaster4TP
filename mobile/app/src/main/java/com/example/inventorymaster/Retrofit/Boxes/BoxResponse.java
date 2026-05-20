package com.example.inventorymaster.Retrofit.Boxes;

import com.example.inventorymaster.DataModels.Box;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoxResponse {
    @SerializedName("items")
    private List<Item> items;

    public List<Box> getBoxes() {
        final List<Box> boxes = new ArrayList<>();

        for(var item : items) boxes.add(new Box(item.id, item.code));
        return boxes;
    }

    public static class Item {
        @SerializedName("id")
        public int id;
        @SerializedName("code")
        public String code;
    }
}
