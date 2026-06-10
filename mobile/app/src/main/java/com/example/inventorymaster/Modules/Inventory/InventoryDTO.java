package com.example.inventorymaster.Modules.Inventory;

import com.google.gson.annotations.SerializedName;

public class InventoryDTO {
    public static class GetTotalBoxesResponse {
        public interface IHandler {
            void take(int data);
        }

        public int data() {
            return totalBoxes;
        }

        @SerializedName("totalBoxes")
        private int totalBoxes;
    }

    public static class GetTotalItemsResponse {
        public interface IHandler {
            void take(int data);
        }

        public int data() {
            return totalItems;
        }

        @SerializedName("totalItems")
        private int totalItems;
    }

    public static class GetTotalItemInstancesResponse {
        public interface IHandler {
            void take(int data);
        }

        public int data() {
            return totalItemInstances;
        }

        @SerializedName("totalItemInstances")
        private int totalItemInstances;
    }

    public static class GetSummaryResponse {
        public interface IHandler {
            void take(int boxes, int items, int itemInstances, double value);
        }

        public int boxes() {
            return totalBoxes;
        }

        public int items() {
            return totalItems;
        }

        public int itemInstances() {
            return totalItemInstances;
        }

        public double value() {
            return totalValue;
        }

        @SerializedName("totalBoxes")
        private int totalBoxes;
        @SerializedName("totalItems")
        private int totalItems;
        @SerializedName("totalItemInstances")
        private int totalItemInstances;
        @SerializedName("totalValue")
        private double totalValue;
    }
}
