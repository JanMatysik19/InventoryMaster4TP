package com.example.inventorymaster.Modules.Boxes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoxDTO {
    public static class GetBoxesResponse {
        public interface IHandler {
            void take(List<Box> data);
        }

        public List<Box> data() {
            if(itemsData == null) return null;
            final List<Box> data = new ArrayList<>();
            for(final var i : itemsData) data.add(new Box(i.id, i.code));
            return  data;
        }

        public int totalPages() {
            return total;
        }


        @SerializedName("items")
        private List<DataItem> itemsData;

        @SerializedName("total")
        private int total;
    }

    public static class GetBoxResponse extends DataItem {
        public interface IHandler {
            void take(Box data);
        }

        public Box data() {
            if(createdAt == null) return null;
            return new Box(id, code);
        }
    }

    public static class DataItem {
        @SerializedName("id")
        public int id;
        @SerializedName("code")
        public String code;
        @SerializedName("createdAt")
        public String createdAt;
    }
}
