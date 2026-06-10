package com.example.inventorymaster.Utils;

public class Stringer {
    public static String padLeft(int number, int length) {
        String value = String.valueOf(number);
        StringBuilder sb = new StringBuilder();

        for (int i = value.length(); i < length; i++) {
            sb.append('0');
        }

        sb.append(value);
        return sb.toString();
    }
}
