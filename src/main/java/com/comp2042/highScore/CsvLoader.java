package com.comp2042.highScore;

import com.comp2042.util.GameChoice;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to load, store, and save Tetris score records from/to a CSV file.
 *
 * <p>The CSV file path is defined as {@code src/main/resources/csv/scores.csv}.
 * Each record maps a {@link GameChoice} (game mode) to a {@link ScoreRecord}.
 * Provides methods to load records, retrieve records by mode, update records,
 * and persist changes back to the CSV.</p>
 */
public class CsvLoader {

    /** Path to the CSV file storing the score records. */
    private static final String FILE_PATH = "src/main/resources/csv/scores.csv";

    /** Map of game mode to its corresponding score record. */
    private final Map<GameChoice, ScoreRecord> records = new HashMap<>();

    /**
     * Loads all score records from the CSV file into memory.
     * If the file is missing or unreadable, prints an error to standard error.
     */
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            br.readLine();// skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                GameChoice mode = GameChoice.valueOf(parts[0]);

                Duration time = "null".equals(parts[1])
                        ? null
                        : Duration.ofSeconds(Long.parseLong(parts[1]));

                int score = Integer.parseInt(parts[2]);
                int rows = Integer.parseInt(parts[3]);

                records.put(mode, new ScoreRecord(mode, time, score, rows));
            }

        } catch (IOException e) {
            System.err.println("CSV missing");
        }
    }

    /**
     * Retrieves the score record for the specified game mode.
     *
     * @param mode the {@link GameChoice} representing the game mode
     * @return the {@link ScoreRecord} associated with the mode, or null if not present
     */
    public ScoreRecord get(GameChoice mode) {
        return records.get(mode);
    }

    /**
     * Updates the score record for a specific game mode and immediately
     * saves the changes to the CSV file.
     *
     * @param mode the {@link GameChoice} representing the game mode
     * @param updated the new {@link ScoreRecord} to store
     */
    public void update(GameChoice mode, ScoreRecord updated) {
        records.put(mode, updated);
        save();
    }

    /**
     * Saves all current score records in memory to the CSV file.
     * Writes the header line first, then each record on a separate line.
     * Null durations are stored as the string "null".
     */
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("mode,time,score,rows\n");

            for (ScoreRecord r : records.values()) {
                String t = (r.time == null) ? "null"
                        : String.valueOf(r.time.getSeconds());

                bw.write(r.mode + ","  + t + "," + r.score + "," + r.rows + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




