package datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextParsingStrategy implements FileParsingStrategy {
    
    @Override
    public List<String[]> parseFile(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] fields = line.split("\\s+");
                records.add(fields);
            }
        }
        
        return records;
    }
}

