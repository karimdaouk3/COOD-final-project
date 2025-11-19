package common;

public class PopulationData {
    private final String zipCode;
    private final int population;

    public PopulationData(String zipCode, int population) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new IllegalArgumentException("ZIP code cannot be null or empty");
        }
        if (population < 0) {
            throw new IllegalArgumentException("Population cannot be negative");
        }

        this.zipCode = zipCode.trim();
        this.population = population;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getPopulation() {
        return population;
    }
}

