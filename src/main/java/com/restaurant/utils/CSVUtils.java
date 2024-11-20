package com.restaurant.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    // Method to read a CSV file and return its content as a list of string arrays.
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line of the CSV file and split it by commas.
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            // Print an error message if there is an issue reading the file.
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return data;
    }

    // Method to write data to a CSV file.
    public static void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }
    }
}

