package com.comp2042.highScore;

import com.comp2042.util.GameChoice;

import java.time.Duration;

/**
 * Represents a score record for a specific Tetris game mode.
 *
 * <p>Each record stores the game mode, the duration of the game, the
 * score achieved, and the number of cleared rows. This class is used
 * by {@link CsvLoader} and {@link com.comp2042.setters.LeaderBoardSetter}to manage leaderboard data.</p>
 */
public class ScoreRecord {
    public GameChoice mode;
    public Duration time;
    public int score;
    public int rows;

    /**
     * Constructs a new score record.
     *
     * @param mode the {@link GameChoice} representing the game mode
     * @param time the duration of the game, or {@code null} if unknown
     * @param score the score achieved
     * @param rows the number of rows cleared
     */
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

