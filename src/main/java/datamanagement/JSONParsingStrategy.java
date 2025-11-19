package datamanagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParsingStrategy implements FileParsingStrategy {
    
    @Override
    public List<String[]> parseFile(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object obj;
            try {
                obj = parser.parse(reader);
            } catch (ParseException e) {
                throw new IOException("Failed to parse JSON file: " + e.getMessage(), e);
            }

            if (!(obj instanceof JSONArray)) {
                throw new IllegalArgumentException("JSON file must contain an array of objects");
            }

            JSONArray jsonArray = (JSONArray) obj;

            for (Object item : jsonArray) {
                if (!(item instanceof JSONObject)) {
                    continue;
                }

                JSONObject jsonObject = (JSONObject) item;
                String[] fields = extractFieldsFromJSON(jsonObject);
                records.add(fields);
            }
        }

        return records;
    }

    private String[] extractFieldsFromJSON(JSONObject jsonObject) {
        List<String> fields = new ArrayList<>();
        
        String date = getStringValue(jsonObject, "date");
        String fine = getStringValue(jsonObject, "fine");
        String violation = getStringValue(jsonObject, "violation");
        String plateId = getStringValue(jsonObject, "plate_id");
        String state = getStringValue(jsonObject, "state");
        String ticketNumber = getStringValue(jsonObject, "ticket_number");
        String zipCode = getStringValueOptional(jsonObject, "zip_code");

        fields.add(date);
        fields.add(fine);
        fields.add(violation);
        fields.add(plateId);
        fields.add(state);
        fields.add(ticketNumber);
        fields.add(zipCode);

        return fields.toArray(new String[0]);
    }

    private String getStringValue(JSONObject jsonObject, String fieldName) {
        Object value = jsonObject.get(fieldName);
        if (value == null) {
            throw new IllegalArgumentException("Required field '" + fieldName + "' is missing or null");
        }
        return String.valueOf(value);
    }

    private String getStringValueOptional(JSONObject jsonObject, String fieldName) {
        Object value = jsonObject.get(fieldName);
        if (value == null) {
            return null;
        } else {
            return String.valueOf(value);
        }
    }
}

