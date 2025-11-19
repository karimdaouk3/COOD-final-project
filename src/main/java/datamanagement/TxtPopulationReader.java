package datamanagement;

import common.PopulationData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtPopulationReader {
    private final String filePath;
    private final FileParsingStrategy parsingStrategy;

    public TxtPopulationReader(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
        this.parsingStrategy = new TextParsingStrategy();
    }

    public TxtPopulationReader(String filePath, FileParsingStrategy parsingStrategy) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (parsingStrategy == null) {
            throw new IllegalArgumentException("Parsing strategy cannot be null");
        }
        this.filePath = filePath;
        this.parsingStrategy = parsingStrategy;
    }

    public List<PopulationData> readPopulationData() throws IOException {
        List<PopulationData> populationList = new ArrayList<>();
        List<String[]> records = parsingStrategy.parseFile(filePath);

        for (String[] parts : records) {
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid line format: expected at least 2 fields");
            }

            String zipCode = parts[0];
            int population;
            try {
                population = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid population value: " + parts[1], e);
            }

            PopulationData data = new PopulationData(zipCode, population);
            populationList.add(data);
        }

        return populationList;
    }

    public String getFilePath() {
        return filePath;
    }
}

