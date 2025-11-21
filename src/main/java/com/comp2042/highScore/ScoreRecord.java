package com.comp2042.highScore;

import com.comp2042.util.GameChoice;

import java.time.Duration;

public class ScoreRecord {
    public GameChoice mode;
    public Duration time;
    public int score;
    public int rows;

    public ScoreRecord(GameChoice mode, Duration time, int score, int rows) {
        this.mode = mode;
        this.time = time;
        this.score = score;
        this.rows = rows;
    }

    // Getters
    public GameChoice getMode() { return mode; }
    public Duration getTime() { return time; }
    public int getScore() { return score; }
    public int getRows() { return rows; }
}

