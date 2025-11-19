package datamanagement;

import common.PopulationData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TxtPopulationReaderTest {

    @Test
    void testReadPopulationData_ValidFile() throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource("population.txt");
        assertNotNull(resourceUrl, "Test resource file not found");
        String testFilePath = URLDecoder.decode(resourceUrl.getPath(), StandardCharsets.UTF_8);
        TxtPopulationReader reader = new TxtPopulationReader(testFilePath);

        List<PopulationData> populationList = reader.readPopulationData();

        assertNotNull(populationList);
        assertFalse(populationList.isEmpty());

        PopulationData first = populationList.get(0);
        assertEquals("19102", first.getZipCode());
        assertEquals(4705, first.getPopulation());

        PopulationData second = populationList.get(1);
        assertEquals("19103", second.getZipCode());
        assertEquals(21908, second.getPopulation());

        for (PopulationData data : populationList) {
            assertNotNull(data.getZipCode());
            assertFalse(data.getZipCode().isEmpty());
            assertTrue(data.getPopulation() >= 0);
        }
    }

    @Test
    void testReadPopulationData_FileNotFound() {
        TxtPopulationReader reader = new TxtPopulationReader("nonexistent_file.txt");

        assertThrows(IOException.class, () -> {
            reader.readPopulationData();
        });
    }

    @Test
    void testConstructor_NullFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TxtPopulationReader(null);
        });
    }

    @Test
    void testConstructor_EmptyFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TxtPopulationReader("");
        });
    }

    @Test
    void testGetFilePath() {
        String filePath = "test.txt";
        TxtPopulationReader reader = new TxtPopulationReader(filePath);
        assertEquals(filePath, reader.getFilePath());
    }
}

