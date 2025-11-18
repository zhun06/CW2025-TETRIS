package com.comp2042.highScore;

import com.comp2042.util.GameChoice;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class CsvLoader {

    private static final String FILE_PATH = "src/main/resources/csv/scores.csv";

    private final Map<GameChoice, ScoreRecord> records = new HashMap<>();

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            br.readLine();// skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                GameChoice mode = GameChoice.valueOf(parts[0]);
                int level = Integer.parseInt(parts[1]);

                Duration time = "null".equals(parts[2])
                        ? null
                        : Duration.ofSeconds(Long.parseLong(parts[2]));

                int score = Integer.parseInt(parts[3]);
                int rows = Integer.parseInt(parts[4]);

                records.put(mode, new ScoreRecord(mode, level, time, score, rows));
            }

        } catch (IOException e) {
            System.err.println("CSV missing");
        }
    }

    public ScoreRecord get(GameChoice mode) {
        return records.get(mode);
    }

    public void update(GameChoice mode, ScoreRecord updated) {
        records.put(mode, updated);
        save();
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("mode,level,time,score,rows\n");

            for (ScoreRecord r : records.values()) {
                String t = (r.time == null) ? "null"
                        : String.valueOf(r.time.getSeconds());

                bw.write(r.mode + "," + r.level + "," + t + "," + r.score + "," + r.rows + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




