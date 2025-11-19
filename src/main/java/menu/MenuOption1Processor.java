package menu;

import common.PopulationData;
import datamanagement.TxtPopulationReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuOption1Processor {

    protected List<PopulationData> populationData;

    public MenuOption1Processor(TxtPopulationReader txtPopulationReader) {
        try {
            this.populationData = txtPopulationReader.readPopulationData();
        } catch (IOException e) {
            System.err.println("Error reading population data: " + e.getMessage());
            this.populationData = new ArrayList<>();
        }
    }

    public int calculateTotalPopulation() {
        if (populationData == null) {
            return 0;
        }
        int totalPopulation = 0;
        for (PopulationData pd : populationData) {
            if (pd != null && pd.getZipCode() != null && !pd.getZipCode().isEmpty()) {
                totalPopulation += pd.getPopulation();
            }
        }
        return totalPopulation;
    }

}