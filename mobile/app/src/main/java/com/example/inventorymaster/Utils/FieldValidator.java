package com.example.inventorymaster.Utils;

public class FieldValidator {
    public static class ItemInstance {
        public static boolean validateItemId(String data) {
            return false;
        }

        public static boolean validateFeaturesCode(String data) {
            return false;
        }
    }

    public static class Item {
        public static boolean validateFeaturesCode(String data) {
            if (data == null || data.isBlank()) {
                return false;
            }

            return data.matches("^[a-zA-Z0-9]{2,10}$");
        }

        public static boolean validateDescription(String data) {
            if (data == null) {
                return false;
            }

            String trimmed = data.trim();

            return trimmed.length() >= 5 && trimmed.length() <= 170;
        }
    }
}