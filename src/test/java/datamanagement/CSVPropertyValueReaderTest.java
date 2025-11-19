package datamanagement;

import common.PropertyValue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVPropertyValueReaderTest {

    @Test
    void testReadPropertyValues_ValidFile() throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource("properties.csv");
        assertNotNull(resourceUrl, "Test resource file not found");
        String testFilePath = URLDecoder.decode(resourceUrl.getPath(), StandardCharsets.UTF_8);
        CSVPropertyValueReader reader = new CSVPropertyValueReader(testFilePath);

        List<PropertyValue> properties = reader.readPropertyValues();

        assertNotNull(properties);
        assertFalse(properties.isEmpty());

        PropertyValue first = properties.get(0);
        assertEquals("191481303", first.getZipCode());
        assertTrue(first.getMarketValue() > 0);
        assertNotNull(first.getOwner1());

        for (PropertyValue property : properties) {
            assertNotNull(property.getZipCode());
            assertFalse(property.getZipCode().isEmpty());
            assertTrue(property.getMarketValue() >= 0);
            if (property.getSalePrice() != null) {
                assertTrue(property.getSalePrice() >= 0);
            }
        }
    }

    @Test
    void testReadPropertyValues_FileNotFound() {
        CSVPropertyValueReader reader = new CSVPropertyValueReader("nonexistent_file.csv");

        assertThrows(IOException.class, () -> {
            reader.readPropertyValues();
        });
    }

    @Test
    void testConstructor_NullFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVPropertyValueReader(null);
        });
    }

    @Test
    void testConstructor_EmptyFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVPropertyValueReader("");
        });
    }

    @Test
    void testGetFilePath() {
        String filePath = "test.csv";
        CSVPropertyValueReader reader = new CSVPropertyValueReader(filePath);
        assertEquals(filePath, reader.getFilePath());
    }
}


