package datamanagement;

import common.PropertyValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVPropertyValueReader {
    private final String filePath;
    private final FileParsingStrategy parsingStrategy;

    public CSVPropertyValueReader(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
        this.parsingStrategy = new CSVParsingStrategy();
    }

    public CSVPropertyValueReader(String filePath, FileParsingStrategy parsingStrategy) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (parsingStrategy == null) {
            throw new IllegalArgumentException("Parsing strategy cannot be null");
        }
        this.filePath = filePath;
        this.parsingStrategy = parsingStrategy;
    }

    public List<PropertyValue> readPropertyValues() throws IOException {
        List<PropertyValue> properties = new ArrayList<>();
        List<String[]> records = parsingStrategy.parseFile(filePath);

        if (records.isEmpty()) {
            return properties;
        }

        String[] headerParts = records.get(0);
        Map<String, Integer> columnIndexMap = parseHeader(headerParts);

        for (int i = 1; i < records.size(); i++) {
            String[] parts = records.get(i);
            try {
                PropertyValue property = parsePropertyValue(parts, columnIndexMap);
                properties.add(property);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        return properties;
    }

    private Map<String, Integer> parseHeader(String[] headers) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            columnIndexMap.put(headers[i].trim(), i);
        }
        return columnIndexMap;
    }

    private PropertyValue parsePropertyValue(String[] parts, Map<String, Integer> columnIndexMap) {
        String zipCode = getField(parts, columnIndexMap, "zip_code");
        double marketValue = getDoubleFieldOptional(parts, columnIndexMap, "market_value", 0.0);
        Double salePrice = getDoubleFieldOptional(parts, columnIndexMap, "sale_price");
        String saleDate = getFieldOptional(parts, columnIndexMap, "sale_date");
        String owner1 = getFieldOptional(parts, columnIndexMap, "owner_1");
        String streetName = getFieldOptional(parts, columnIndexMap, "street_name");
        String houseNumber = getFieldOptional(parts, columnIndexMap, "house_number");
        double livableArea = getDoubleFieldOptional(parts, columnIndexMap, "total_livable_area", 0.0);

        return new PropertyValue(zipCode, marketValue, salePrice, saleDate, owner1, streetName, houseNumber, livableArea);
    }

    private String getField(String[] parts, Map<String, Integer> columnIndexMap, String fieldName) {
        Integer index = columnIndexMap.get(fieldName);
        if (index == null || index >= parts.length) {
            throw new IllegalArgumentException("Required field '" + fieldName + "' not found in CSV");
        }
        String value = parts[index].trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Required field '" + fieldName + "' is empty");
        }
        return value;
    }

    private String getFieldOptional(String[] parts, Map<String, Integer> columnIndexMap, String fieldName) {
        Integer index = columnIndexMap.get(fieldName);
        if (index == null || index >= parts.length) {
            return null;
        }
        String value = parts[index].trim();
        if (value.isEmpty()) {
            return null;
        } else {
            return value;
        }
    }

    private double getDoubleField(String[] parts, Map<String, Integer> columnIndexMap, String fieldName) {
        String value = getField(parts, columnIndexMap, fieldName);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field '" + fieldName + "' cannot be converted to a number: " + value, e);
        }
    }

    private double getDoubleFieldOptional(String[] parts, Map<String, Integer> columnIndexMap, String fieldName, double defaultValue) {
        String value = getFieldOptional(parts, columnIndexMap, fieldName);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Double getDoubleFieldOptional(String[] parts, Map<String, Integer> columnIndexMap, String fieldName) {
        String value = getFieldOptional(parts, columnIndexMap, fieldName);
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getFilePath() {
        return filePath;
    }
}

