package datamanagement;

import java.io.IOException;
import java.util.List;

public interface FileParsingStrategy {
    List<String[]> parseFile(String filePath) throws IOException;
}

