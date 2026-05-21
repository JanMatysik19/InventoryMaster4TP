package com.example.inventorymaster.Utils;

public class FieldValidator {
    public static class Category {
        public static boolean validateCode(String data) {
            return false;
        }
    }

    public static class ItemInstance {
        public static boolean validateItemId(String data) {
            return false;
        }

        public static boolean validateFeaturesCode(String data) {
            return false;
        }
    }
}
