package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParkingViolation {
    private final LocalDateTime timestamp;
    private final double fine;
    private final String description;
    private final String vehicleId;
    private final String state;
    private final String violationId;
    private final String zipCode;

    public ParkingViolation(String timestamp, double fine, String description,
                           String vehicleId, String state, String violationId, String zipCode) {
        if (timestamp == null || timestamp.trim().isEmpty()) {
            throw new IllegalArgumentException("Timestamp cannot be null or empty");
        }
        if (fine < 0) {
            throw new IllegalArgumentException("Fine cannot be negative");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        if (violationId == null) {
            throw new IllegalArgumentException("Violation ID cannot be null");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            this.timestamp = LocalDateTime.parse(timestamp, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format: " + timestamp, e);
        }

        this.fine = fine;
        this.description = description;
        this.vehicleId = vehicleId;
        this.state = state;
        this.violationId = violationId;
        
        if (zipCode == null || zipCode.trim().isEmpty()) {
            this.zipCode = null;
        } else {
            this.zipCode = zipCode.trim();
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getFine() {
        return fine;
    }

    public String getDescription() {
        return description;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getState() {
        return state;
    }

    public String getViolationId() {
        return violationId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public boolean hasZipCode() {
        return zipCode != null && !zipCode.isEmpty();
    }
}

