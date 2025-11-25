package com.comp2042.logic.time;

import java.time.Duration;
import java.time.Instant;

/**
 * A simple stopwatch that counts elapsed time upward.
 * <p>
 * The stopwatch supports start, pause, and resume operations and
 * internally tracks accumulated running time.
 * </p>
 */
public class Stopwatch {

    /** Timestamp when the stopwatch last started. */
    private Instant startTime;

    /** Accumulated elapsed duration while running. */
    private Duration accumulated = Duration.ZERO;

    /** Whether the stopwatch is currently running. */
    private boolean running = false;

    /**
     * Starts the stopwatch, resetting all previous elapsed time.
     */
    public void start() {
        accumulated = Duration.ZERO;
        startTime = Instant.now();
        running = true;
    }

    /**
     * Pauses the stopwatch and stores the elapsed time so far.
     */
    public void pause() {
        if (!running) return;

        accumulated = accumulated.plus(Duration.between(startTime, Instant.now()));
        running = false;
        startTime = null;
    }

    /**
     * Resumes the stopwatch from the last accumulated state.
     */
    public void resume() {
        if (running) return;

        startTime = Instant.now();
        running = true;
    }

    /**
     * Returns the elapsed time.
     * <p>
     * If the stopwatch is running, this includes the time since it was last resumed.
     * If paused, only the accumulated duration is returned.
     * </p>
     *
     * @return the elapsed duration
     */
    public Duration getElapsed() {
        if (running) {
            return accumulated.plus(Duration.between(startTime, Instant.now()));
        } else {
            return accumulated;
        }
    }
}
