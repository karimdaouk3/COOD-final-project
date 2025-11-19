package menu;

import common.PopulationData;
import datamanagement.TxtPopulationReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuOption1ProcessorCalculateTotalPopulationTest {

    @Test
    void testCalculateTotalPopulation_WithValidData() throws IOException {
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000),
            new PopulationData("19103", 2000)
        );
        TxtPopulationReader mockReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption1Processor processor = new MenuOption1Processor(mockReader);
        int result = processor.calculateTotalPopulation();

        assertEquals(3000, result);
    }

    @Test
    void testCalculateTotalPopulation_WithIOException() throws IOException {
        TxtPopulationReader mockReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                throw new IOException("File error");
            }
        };

        MenuOption1Processor processor = new MenuOption1Processor(mockReader);
        int result = processor.calculateTotalPopulation();

        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalPopulation_WithNullPopulationData() throws IOException {
        List<PopulationData> populationData = new ArrayList<>();
        populationData.add(new PopulationData("19104", 1000));
        populationData.add(null);
        TxtPopulationReader mockReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption1Processor processor = new MenuOption1Processor(mockReader);
        int result = processor.calculateTotalPopulation();

        assertEquals(1000, result);
    }
}
