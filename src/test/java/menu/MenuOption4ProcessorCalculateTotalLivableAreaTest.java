package menu;

import common.PropertyValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuOption4ProcessorCalculateTotalLivableAreaTest {

    @Test
    void testCalculateTotalLivableArea_WithValidData() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0),
            new PropertyValue("19104", 200000.0, null, null, "Owner2", "Oak Ave", "456", 1500.0)
        );

        MenuOption4Processor processor = new MenuOption4Processor(propertyData);
        int result = processor.calculateTotalLivableArea("19104");

        assertEquals(1250, result);
    }

    @Test
    void testCalculateTotalLivableArea_WithNullTarget() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );

        MenuOption4Processor processor = new MenuOption4Processor(propertyData);
        int result = processor.calculateTotalLivableArea(null);

        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalLivableArea_WithNullPropertyValueData() {
        MenuOption4Processor processor = new MenuOption4Processor(null);
        int result = processor.calculateTotalLivableArea("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalLivableArea_WithEmptyList() {
        List<PropertyValue> propertyData = new ArrayList<>();

        MenuOption4Processor processor = new MenuOption4Processor(propertyData);
        int result = processor.calculateTotalLivableArea("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalLivableArea_WithNoMatchingZipCode() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );

        MenuOption4Processor processor = new MenuOption4Processor(propertyData);
        int result = processor.calculateTotalLivableArea("19105");

        assertEquals(0, result);
    }
}
