package menu;

import common.PropertyValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuOption3ProcessorCalculateMarketValueTest {

    @Test
    void testCalculateMarketValue_WithValidData() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0),
            new PropertyValue("19104", 200000.0, null, null, "Owner2", "Oak Ave", "456", 1500.0)
        );

        MenuOption3Processor processor = new MenuOption3Processor(propertyData);
        int result = processor.calculateMarketValue("19104");

        assertEquals(150000, result);
    }

    @Test
    void testCalculateMarketValue_WithNullTarget() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );

        MenuOption3Processor processor = new MenuOption3Processor(propertyData);
        int result = processor.calculateMarketValue(null);

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValue_WithNullPropertyValueData() {
        MenuOption3Processor processor = new MenuOption3Processor(null);
        int result = processor.calculateMarketValue("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValue_WithEmptyList() {
        List<PropertyValue> propertyData = new ArrayList<>();

        MenuOption3Processor processor = new MenuOption3Processor(propertyData);
        int result = processor.calculateMarketValue("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValue_WithNoMatchingZipCode() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );

        MenuOption3Processor processor = new MenuOption3Processor(propertyData);
        int result = processor.calculateMarketValue("19105");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValue_WithMemoization() {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );

        MenuOption3Processor processor = new MenuOption3Processor(propertyData);
        int result1 = processor.calculateMarketValue("19104");
        int result2 = processor.calculateMarketValue("19104");

        assertEquals(100000, result1);
        assertEquals(100000, result2);
    }
}
