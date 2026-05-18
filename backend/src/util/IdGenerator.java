package util;

import java.util.List;

/**
 * Utility class to generate unique IDs sequentially based on existing records in a file.
 */
public class IdGenerator {

    /**
     * Generates the next ID for a given prefix (e.g., S001, A001).
     * 
     * @param prefix   The letter prefix (e.g., "S" for Session, "A" for Attempt)
     * @param filePath The file to read existing records from
     * @return A newly generated ID string
     */
    public static String generateId(String prefix, String filePath) {
        List<String> lines = FileHandler.readFile(filePath);
        if (lines == null || lines.isEmpty()) {
            return prefix + "001";
        }

        int maxId = 0;
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            // Assuming the ID is always the first column in the pipe-separated format
            String idStr = line.split("\\|")[0];
            if (idStr.startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(idStr.substring(prefix.length()));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Ignore improperly formatted IDs
                }
            }
        }

        maxId++;
        return String.format("%s%03d", prefix, maxId);
    }
}
