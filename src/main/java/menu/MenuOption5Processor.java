package menu;

import common.PopulationData;
import common.PropertyValue;
import datamanagement.CSVPropertyValueReader;
import datamanagement.TxtPopulationReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuOption5Processor {

    protected List<PopulationData> populationData;
    protected List<PropertyValue> propertyValueData;

    public MenuOption5Processor(CSVPropertyValueReader propertyValueReader,
                                TxtPopulationReader txtPopulationReader) {
        try {
            this.propertyValueData = propertyValueReader.readPropertyValues();
        } catch (IOException e) {
            System.err.println("Error reading property value data: " + e.getMessage());
            this.propertyValueData = new ArrayList<>();
        }
        
        try {
            this.populationData = txtPopulationReader.readPopulationData();
        } catch (IOException e) {
            System.err.println("Error reading population data: " + e.getMessage());
            this.populationData = new ArrayList<>();
        }
    }

    public int calculateMarketValuePerCapita(String target) {
        if (propertyValueData == null || populationData == null || target == null) {
            return 0;
        }
        
        double marketValue = 0;
        ZipCodeFilteringIterator<PropertyValue> propertyIterator = 
            new ZipCodeFilteringIterator<>(propertyValueData, target, pv -> pv.getZipCode());
        
        while (propertyIterator.hasNext()) {
            PropertyValue pv = propertyIterator.next();
            marketValue += pv.getMarketValue();
        }
        
        int population = -1;
        ZipCodeFilteringIterator<PopulationData> populationIterator = 
            new ZipCodeFilteringIterator<>(populationData, target, pd -> pd.getZipCode());
        
        if (populationIterator.hasNext()) {
            PopulationData pd = populationIterator.next();
            population = pd.getPopulation();
        }
        
        if (marketValue == 0 || population <= 0) {
            return 0;
        }
        return (int) Math.round(marketValue / population);
    }

}
