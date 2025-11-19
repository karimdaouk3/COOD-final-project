package menu;

import common.ParkingViolation;
import common.PopulationData;
import datamanagement.ParkingViolationReader;
import datamanagement.TxtPopulationReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MenuOption2Processor {

    protected List<PopulationData> populationData;
    protected List<ParkingViolation> parkingViolationData;

    public MenuOption2Processor(ParkingViolationReader parkingViolationReader,
                                TxtPopulationReader txtPopulationReader) {
        try {
            this.parkingViolationData = parkingViolationReader.readParkingViolations();
        } catch (IOException e) {
            System.err.println("Error reading parking violations data: " + e.getMessage());
            this.parkingViolationData = new ArrayList<>();
        }
        
        try {
            this.populationData = txtPopulationReader.readPopulationData();
        } catch (IOException e) {
            System.err.println("Error reading population data: " + e.getMessage());
            this.populationData = new ArrayList<>();
        }
    }

    private Map<String, Double> calculateFines() {
        if (parkingViolationData == null) {
            return new HashMap<>();
        }
        
        Map<String, Double> totalFines = new HashMap<>();
        for (ParkingViolation pv : parkingViolationData) {
            if (pv != null && pv.getZipCode() != null && !pv.getZipCode().isEmpty() &&
                pv.getState() != null && !pv.getState().isEmpty() && pv.getState().equals("PA")) {
                totalFines.put(pv.getZipCode(), totalFines.getOrDefault(pv.getZipCode(), 0.0) + pv.getFine());
            }
        }
        return totalFines;
    }

    public Map<String, Double> calculateFinesPerCapita() {
        if (populationData == null) {
            return new TreeMap<>();
        }
        
        Map<String, Double> finesPerCapita = new TreeMap<>();
        Map<String, Double> totalFines = calculateFines();
        
        for (PopulationData pd : populationData) {
            if (pd != null && pd.getZipCode() != null && pd.getPopulation() > 0) {
                Double fineAmount = totalFines.get(pd.getZipCode());
                if (fineAmount != null) {
                    double perCapita = fineAmount / pd.getPopulation();
                    finesPerCapita.put(pd.getZipCode(), Math.round(perCapita * 10000.0) / 10000.0);
                }
            }
        }
        return finesPerCapita;
    }

}
