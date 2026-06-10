package com.example.inventorymaster.Utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FieldValidatorTest {

    @Test
    public void item_validateFeaturesCode_shouldReturnTrueForValidCode() {
        assertTrue(FieldValidator.Item.validateFeaturesCode("ABC123"));
        assertTrue(FieldValidator.Item.validateFeaturesCode("A1")); // length 2
        assertTrue(FieldValidator.Item.validateFeaturesCode("ABCDEFGHIJ")); // length 10
    }

    @Test
    public void item_validateFeaturesCode_shouldReturnFalseForInvalidCode() {
        assertFalse(FieldValidator.Item.validateFeaturesCode(null));
        assertFalse(FieldValidator.Item.validateFeaturesCode(""));
        assertFalse(FieldValidator.Item.validateFeaturesCode(" "));
        assertFalse(FieldValidator.Item.validateFeaturesCode("A")); // too short
        assertFalse(FieldValidator.Item.validateFeaturesCode("ABCDEFGHIJK")); // too long (11)
        assertFalse(FieldValidator.Item.validateFeaturesCode("ABC-123")); // invalid characters
    }

    @Test
    public void item_validateDescription_shouldReturnTrueForValidDescription() {
        assertTrue(FieldValidator.Item.validateDescription("Valid desc"));
        assertTrue(FieldValidator.Item.validateDescription("12345")); // length 5
    }

    @Test
    public void item_validateDescription_shouldReturnFalseForInvalidDescription() {
        assertFalse(FieldValidator.Item.validateDescription(null));
        assertFalse(FieldValidator.Item.validateDescription("1234")); // too short
        assertFalse(FieldValidator.Item.validateDescription("   1234   ")); // too short after trim
        
        StringBuilder longDesc = new StringBuilder();
        for (int i = 0; i < 171; i++) longDesc.append("a");
        assertFalse(FieldValidator.Item.validateDescription(longDesc.toString())); // too long
    }

    @Test
    public void itemInstance_validateItemId_shouldReturnFalse() {
        assertFalse(FieldValidator.ItemInstance.validateItemId("any"));
    }

    @Test
    public void itemInstance_validateFeaturesCode_shouldReturnFalse() {
        assertFalse(FieldValidator.ItemInstance.validateFeaturesCode("any"));
    }
}
