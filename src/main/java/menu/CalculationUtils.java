package menu;

import java.util.List;
import java.util.function.Function;

public class CalculationUtils {
    
    public static <T> int calculateAverage(String target, List<T> dataList, 
                                          Function<T, String> zipCodeExtractor,
                                          Function<T, Double> valueExtractor) {
        if (dataList == null || target == null) {
            return 0;
        }
        
        int count = 0;
        double total = 0;
        
        ZipCodeFilteringIterator<T> iterator = new ZipCodeFilteringIterator<>(dataList, target, zipCodeExtractor);
        
        while (iterator.hasNext()) {
            T item = iterator.next();
            count++;
            Double value = valueExtractor.apply(item);
            if (value != null) {
                total += value;
            }
        }
        
        if (count == 0) {
            return 0;
        }
        return (int) Math.round(total / count);
    }
}

