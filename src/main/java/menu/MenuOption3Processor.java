package menu;

import common.PropertyValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MenuOption3Processor {

    protected List<PropertyValue> propertyValueData;
    private Map<String, Integer> marketValueCache;

    public MenuOption3Processor(List<PropertyValue> propertyValueData) {
        if (propertyValueData != null) {
            this.propertyValueData = propertyValueData;
        } else {
            this.propertyValueData = new java.util.ArrayList<>();
        }
        this.marketValueCache = new HashMap<>();
    }

    public int calculateMarketValue(String target) {
        if (target == null) {
            return 0;
        }
        
        if (marketValueCache.containsKey(target)) {
            return marketValueCache.get(target);
        }
        
        int result = CalculationUtils.calculateAverage(
            target,
            propertyValueData,
            pv -> pv.getZipCode(),
            pv -> pv.getMarketValue()
        );
        
        marketValueCache.put(target, result);
        return result;
    }

}
