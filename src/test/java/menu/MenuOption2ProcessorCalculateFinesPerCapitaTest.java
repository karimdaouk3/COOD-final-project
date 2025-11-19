package menu;

import common.ParkingViolation;
import common.PopulationData;
import datamanagement.ParkingViolationReader;
import datamanagement.TxtPopulationReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MenuOption2ProcessorCalculateFinesPerCapitaTest {

    @Test
    void testCalculateFinesPerCapita_WithValidData() throws IOException {
        List<ParkingViolation> violations = Arrays.asList(
            createParkingViolation("19104", "PA", 50.0),
            createParkingViolation("19103", "PA", 25.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000),
            new PopulationData("19103", 500)
        );

        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                return violations;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertEquals(0.0500, result.get("19103"), 0.0001);
        assertEquals(0.0500, result.get("19104"), 0.0001);
    }

    @Test
    void testCalculateFinesPerCapita_WithNonPAState() throws IOException {
        List<ParkingViolation> violations = Arrays.asList(
            createParkingViolation("19104", "NJ", 50.0),
            createParkingViolation("19104", "PA", 30.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000)
        );

        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                return violations;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertEquals(0.0300, result.get("19104"), 0.0001);
    }

    @Test
    void testCalculateFinesPerCapita_WithNullZipCode() throws IOException {
        List<ParkingViolation> violations = Arrays.asList(
            createParkingViolation(null, "PA", 50.0),
            createParkingViolation("19104", "PA", 30.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 1000)
        );

        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                return violations;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertEquals(0.0300, result.get("19104"), 0.0001);
    }

    @Test
    void testCalculateFinesPerCapita_WithZeroPopulation() throws IOException {
        List<ParkingViolation> violations = Arrays.asList(
            createParkingViolation("19104", "PA", 50.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19104", 0)
        );

        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                return violations;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertTrue(result.isEmpty());
    }

    @Test
    void testCalculateFinesPerCapita_WithNoMatchingFines() throws IOException {
        List<ParkingViolation> violations = Arrays.asList(
            createParkingViolation("19104", "PA", 50.0)
        );
        List<PopulationData> populationData = Arrays.asList(
            new PopulationData("19103", 1000)
        );

        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                return violations;
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return populationData;
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertTrue(result.isEmpty());
    }

    @Test
    void testCalculateFinesPerCapita_WithIOException() throws IOException {
        ParkingViolationReader mockParkingReader = new ParkingViolationReader() {
            @Override
            public List<ParkingViolation> readParkingViolations() throws IOException {
                throw new IOException("File error");
            }
        };
        TxtPopulationReader mockPopulationReader = new TxtPopulationReader("dummy.txt") {
            @Override
            public List<PopulationData> readPopulationData() throws IOException {
                return Arrays.asList(new PopulationData("19104", 1000));
            }
        };

        MenuOption2Processor processor = new MenuOption2Processor(mockParkingReader, mockPopulationReader);
        Map<String, Double> result = processor.calculateFinesPerCapita();

        assertTrue(result.isEmpty());
    }

    private ParkingViolation createParkingViolation(String zipCode, String state, double fine) {
        return new ParkingViolation("2024-01-01T12:00:00Z", fine, "Test violation", "VEH123", state, "VIOL123", zipCode);
    }
}
