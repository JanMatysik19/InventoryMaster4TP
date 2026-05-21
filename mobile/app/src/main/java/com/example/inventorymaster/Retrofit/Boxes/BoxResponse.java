package com.example.inventorymaster.Retrofit.Boxes;

import com.example.inventorymaster.DataModels.Box;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoxResponse {
    public static class Many {
        @SerializedName("items")
        private List<BoxItem> items;

        @SerializedName("total")
        private int total;

        public List<Box> getBoxes() {
            final List<Box> boxes = new ArrayList<>();
            if (items == null) return boxes;

            for(BoxItem item : items) {
                boxes.add(new Box(item.id, item.code));
            }
            return boxes;
        }

        public int getTotal() {
            return total;
        }
    }

    public static class One extends BoxItem {
        public Box getBox() {
            return new Box(id, code);
        }
    }

    public static class BoxItem {
        @SerializedName("id")
        public int id;
        @SerializedName("code")
        public String code;
        @SerializedName("createdAt")
        public String createdAt;
    }
}
