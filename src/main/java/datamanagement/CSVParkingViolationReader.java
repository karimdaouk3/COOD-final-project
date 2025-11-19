package datamanagement;

import common.ParkingViolation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParkingViolationReader implements ParkingViolationReader {
    private final String filePath;
    private final FileParsingStrategy parsingStrategy;

    public CSVParkingViolationReader(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
        this.parsingStrategy = new CSVParsingStrategy();
    }

    public CSVParkingViolationReader(String filePath, FileParsingStrategy parsingStrategy) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (parsingStrategy == null) {
            throw new IllegalArgumentException("Parsing strategy cannot be null");
        }
        this.filePath = filePath;
        this.parsingStrategy = parsingStrategy;
    }

    public List<ParkingViolation> readParkingViolations() throws IOException {
        List<ParkingViolation> violations = new ArrayList<>();
        List<String[]> records = parsingStrategy.parseFile(filePath);

        for (String[] parts : records) {
            if (parts.length < 7) {
                throw new IllegalArgumentException("Invalid CSV line format: expected 7 fields");
            }

            String timestamp = parts[0];
            double fine = parseDouble(parts[1]);
            String description = parts[2];
            String vehicleId = parts[3];
            String state = parts[4];
            String violationId = parts[5];
            String zipCode;
            if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                zipCode = parts[6];
            } else {
                zipCode = null;
            }

            ParkingViolation violation = new ParkingViolation(timestamp, fine, description, 
                                                              vehicleId, state, violationId, zipCode);
            violations.add(violation);
        }

        return violations;
    }

    private double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot parse empty value as double");
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse value as double: " + value, e);
        }
    }

    public String getFilePath() {
        return filePath;
    }
}


