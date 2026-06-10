package com.example.inventorymaster.Modules.ItemInstances;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemInstanceDTO {
    public static class GetItemInstancesResponse {
        public interface IHandler {
            void take(List<ItemInstance> data);
        }

        @SerializedName("items")
        private List<DataItem> items;

        @SerializedName("total")
        private int total;

        public List<ItemInstance> data() {
            if (items == null) return null;
            final List<ItemInstance> data = new ArrayList<>();
            for(DataItem item : items) data.add(new ItemInstance(item.id, item.itemId, item.sequenceNumber, item.boxId, item.boxCode));
            return data;
        }

        public int totalPages() {
            return total;
        }
    }

    public static class GetItemInstanceResponse extends DataItem {
        public interface IHandler {
            void take(ItemInstance data);
        }

        public ItemInstance data() {
            return new ItemInstance(id, itemId, sequenceNumber, boxId, boxCode);
        }
    }

    public static class AddItemInstanceRequest {
        @SerializedName("itemId")
        public int itemId;

        public AddItemInstanceRequest(int itemId) {
            this.itemId = itemId;
        }
    }

    public static class MoveItemInstanceRequest {
        @SerializedName("boxId")
        public int boxId;

        public MoveItemInstanceRequest(int boxId) { this.boxId = boxId; }
    }

    public static class DataItem {
        @SerializedName("id")
        public int id;
        @SerializedName("sequenceNumber")
        public int sequenceNumber;
        @SerializedName("itemId")
        public int itemId;
        @SerializedName("boxId")
        public int boxId;
        @SerializedName("boxCode")
        public String boxCode;
        @SerializedName("createdAt")
        public String createdAt;
    }
}
