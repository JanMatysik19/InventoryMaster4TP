package com.example.inventorymaster.Utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringerTest {

    @Test
    public void padLeft_shouldPadWithZeros() {
        assertEquals("00123", Stringer.padLeft(123, 5));
        assertEquals("00001", Stringer.padLeft(1, 5));
    }

    @Test
    public void padLeft_shouldNotPadIfNumberIsLongerThanLength() {
        assertEquals("12345", Stringer.padLeft(12345, 3));
    }

    @Test
    public void padLeft_shouldHandleZero() {
        assertEquals("000", Stringer.padLeft(0, 3));
    }
}
