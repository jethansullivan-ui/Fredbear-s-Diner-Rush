package com.fredbeardinerrush.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileTallyPersistence implements ITallyPersistence {
    private final String filePath;

    public FileTallyPersistence(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int[] loadTally() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new int[] { 0, 0 }; // Default tallies if no file exists yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int p1Wins = Integer.parseInt(parts[0].trim());
                    int p2Wins = Integer.parseInt(parts[1].trim());
                    return new int[] { p1Wins, p2Wins };
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not load tally data, using defaults: " + e.getMessage());
        }

        return new int[] { 0, 0 };
    }

    @Override
    public void saveTally(int p1Wins, int p2Wins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(p1Wins + "," + p2Wins);
        } catch (IOException e) {
            System.err.println("Could not save tally data: " + e.getMessage());
        }
    }
}