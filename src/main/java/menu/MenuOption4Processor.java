package menu;

import common.PropertyValue;
import java.util.List;
import java.util.function.Function;

public class MenuOption4Processor {

    protected List<PropertyValue> propertyValueData;

    public MenuOption4Processor(List<PropertyValue> propertyValueData) {
        if (propertyValueData != null) {
            this.propertyValueData = propertyValueData;
        } else {
            this.propertyValueData = new java.util.ArrayList<>();
        }
    }

    public int calculateTotalLivableArea(String target) {
        return CalculationUtils.calculateAverage(
            target,
            propertyValueData,
            pv -> pv.getZipCode(),
            pv -> pv.getLivableArea()
        );
    }

}
