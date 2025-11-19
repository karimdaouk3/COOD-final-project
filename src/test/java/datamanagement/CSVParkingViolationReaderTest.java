package datamanagement;

import common.ParkingViolation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVParkingViolationReaderTest {

    @Test
    void testReadParkingViolations_ValidFile() throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource("parking.csv");
        assertNotNull(resourceUrl, "Test resource file not found");
        String testFilePath = URLDecoder.decode(resourceUrl.getPath(), StandardCharsets.UTF_8);
        CSVParkingViolationReader reader = new CSVParkingViolationReader(testFilePath);

        List<ParkingViolation> violations = reader.readParkingViolations();

        assertNotNull(violations);
        assertFalse(violations.isEmpty());

        ParkingViolation first = violations.get(0);
        assertEquals("19104", first.getZipCode());
        assertEquals("PA", first.getState());
        assertEquals(36.0, first.getFine(), 0.01);
        assertEquals("1322731", first.getVehicleId());
        assertEquals("2905938", first.getViolationId());
        assertEquals("METER EXPIRED CC", first.getDescription());
        assertTrue(first.hasZipCode());

        for (ParkingViolation violation : violations) {
            assertNotNull(violation.getTimestamp());
            assertNotNull(violation.getDescription());
            assertNotNull(violation.getVehicleId());
            assertNotNull(violation.getState());
            assertNotNull(violation.getViolationId());
            assertTrue(violation.getFine() >= 0);
        }

        boolean foundEmptyZip = false;
        for (ParkingViolation violation : violations) {
            if (!violation.hasZipCode()) {
                foundEmptyZip = true;
                assertNull(violation.getZipCode());
                break;
            }
        }
        assertTrue(foundEmptyZip, "Should have at least one violation with empty zip code");
    }

    @Test
    void testReadParkingViolations_FileNotFound() {
        CSVParkingViolationReader reader = new CSVParkingViolationReader("nonexistent_file.csv");

        assertThrows(IOException.class, () -> {
            reader.readParkingViolations();
        });
    }

    @Test
    void testConstructor_NullFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVParkingViolationReader(null);
        });
    }

    @Test
    void testConstructor_EmptyFilePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CSVParkingViolationReader("");
        });
    }

    @Test
    void testGetFilePath() {
        String filePath = "test.csv";
        CSVParkingViolationReader reader = new CSVParkingViolationReader(filePath);
        assertEquals(filePath, reader.getFilePath());
    }
}


