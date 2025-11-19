package datamanagement;

import common.ParkingViolation;
import java.io.IOException;
import java.util.List;

public interface ParkingViolationReader {
    List<ParkingViolation> readParkingViolations() throws IOException;
}

