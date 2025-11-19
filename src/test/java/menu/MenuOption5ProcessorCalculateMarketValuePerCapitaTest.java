package menu;

import common.PopulationData;
import common.PropertyValue;
import datamanagement.CSVPropertyValueReader;
import datamanagement.TxtPopulationReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuOption5ProcessorCalculateMarketValuePerCapitaTest {

    @Test
    void testCalculateMarketValuePerCapita_WithValidData() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0),
            new PropertyValue("19104", 200000.0, null, null, "Owner2", "Oak Ave", "456", 1500.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000)
        );

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19104");

        assertEquals(300, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithNullTarget() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000)
        );

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita(null);

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithNoMatchingZipCode() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19103", 1000)
        );

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19105");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithZeroMarketValue() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 0.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000)
        );

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithZeroPopulation() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 0)
        );

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithNoPopulationData() throws IOException {
        List<PropertyValue> propertyData = Arrays.asList(
            new PropertyValue("19104", 100000.0, null, null, "Owner1", "Main St", "123", 1000.0)
        );
        List<PopulationData> populationData = new ArrayList<>();

        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                return propertyData;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19104");

        assertEquals(0, result);
    }

    @Test
    void testCalculateMarketValuePerCapita_WithIOException() throws IOException {
        CSVPropertyValueReader mockPropertyReader = new CSVPropertyValueReader("dummy.csv") {
            @Override
            public List<PropertyValue> readPropertyValues() throws IOException {
                throw new IOException("File error");
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return Arrays.asList(new PopulationData("19104", 1000));
            }
        };

        MenuOption5Processor processor = new MenuOption5Processor(mockPropertyReader, mockPopulationReader);
        int result = processor.calculateMarketValuePerCapita("19104");

        assertEquals(0, result);
    }
}
