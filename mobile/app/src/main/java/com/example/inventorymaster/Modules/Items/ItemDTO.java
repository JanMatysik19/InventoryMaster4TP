package com.example.inventorymaster.Modules.Items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemDTO {
    public static class GetItemsResponse {
        public interface IHandler {
            void take(List<Item> data);
        }

        public List<Item> data() {
            if(itemsData == null) return null;
            final List<Item> data = new ArrayList<>();
            for(final var i : itemsData) data.add(new Item(i.id, i.featuresCode, i.description, i.price, i.quantity));
            return data;
        }

        public int totalPages() {
            return total;
        }

        @SerializedName("items")
        private List<DataItem> itemsData;

        @SerializedName("total")
        private int total;
    }

    public static class GetItemResponse extends DataItem {
        public interface IHandler {
            void take(Item data);
        }

        public Item data() {
            return new Item(id, featuresCode, description, price, quantity);
        }
    }

    public static class AddItemRequest {
        public AddItemRequest(String featuresCode, String description, String price) {
            this.featuresCode = featuresCode;
            this.description = description;
            this.price = price;
        }

        @SerializedName("featuresCode")
        public String featuresCode;
        @SerializedName("description")
        public String description;
        @SerializedName("price")
        public String price;
    }

    public static class UpdateItemRequest {
        public UpdateItemRequest(String featuresCode, String description, String price) {
            this.featuresCode = featuresCode;
            this.description = description;
            this.price = price;
        }

        @SerializedName("featuresCode")
        public String featuresCode;
        @SerializedName("description")
        public String description;
        @SerializedName("price")
        public String price;
    }


    public static class DataItem {
        @SerializedName("id")
        public int id;
        @SerializedName("featuresCode")
        public String featuresCode;
        @SerializedName("description")
        public String description;
        @SerializedName("price")
        public String price;
        @SerializedName("quantity")
        public int quantity;
        @SerializedName("createdAt")
        public String createdAt;
    }
}
