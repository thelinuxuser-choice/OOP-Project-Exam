package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle safe reading, writing, and appending to a file.
 */
public class FileHandler {

    /**
     * Reads all lines from the specified file.
     * @param filePath Path to the file
     * @return List of strings, where each string is a line from the file
     */
    public static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        
        // Return an empty list if file does not exist yet
        if (!file.exists()) {
            return lines;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + filePath + " - " + e.getMessage());
        }
        
        return lines;
    }

    /**
     * Writes a list of strings to the specified file. Overwrites the file if it exists.
     * @param filePath Path to the file
     * @param data List of strings to write
     */
    public static void writeFile(String filePath, List<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * Appends a single line of text to the end of the file.
     * @param filePath Path to the file
     * @param line String line to append
     */
    public static void appendFile(String filePath, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to file: " + filePath + " - " + e.getMessage());
        }
    }
}
