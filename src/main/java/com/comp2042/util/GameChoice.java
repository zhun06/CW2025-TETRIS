package com.comp2042.util;

/**
 * Represents the different game modes available in Tetris.
 */
public enum GameChoice {
    /** Relax mode without time pressure. */
    ZEN,
    /** Objective: clear 40 lines as quickly as possible. */
    FORTY_LINES,
    /** Fast-paced mode: clear as many lines as possible in 1 minute. */
    BLITZ,
    /** Challenge mode where the board gradually rises, increasing difficulty. */
    HARDCORE
}
