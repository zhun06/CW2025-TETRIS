package com.comp2042.logic.time;

import java.time.Duration;
import java.time.Instant;

/**
 * A countdown timer that counts remaining time downward.
 * <p>
 * Supports start, pause, resume, and expiration checks.
 * </p>
 */
public class Timer {

    /** The timestamp when the timer will reach zero. */
    private Instant endTime;

    /** Remaining time when the timer was paused. */
    private Duration remainingWhenPaused = Duration.ZERO;

    /** Whether the timer is currently paused. */
    private boolean paused = false;

    /** Whether the timer has been started at least once. */
    private boolean started = false;

    /**
     * Starts the timer with the given duration.
     *
     * @param duration the total countdown duration
     */
    public void start(Duration duration) {
        started = true;
        paused = false;
        endTime = Instant.now().plus(duration);
        remainingWhenPaused = Duration.ZERO;
    }

    /**
     * Pauses the countdown and stores how much time is left.
     */
    public void pause() {
        if (!started || paused) return;

        remainingWhenPaused = Duration.between(Instant.now(), endTime);
        paused = true;
    }

    /**
     * Resumes the countdown from the time remaining when paused.
     */
    public void resume() {
        if (!started || !paused) return;

        endTime = Instant.now().plus(remainingWhenPaused);
        paused = false;
    }

    /**
     * Returns the remaining time in the countdown.
     *
     * @return the remaining duration (never negative)
     */
    public Duration getRemaining() {
        if (!started) return Duration.ZERO;

        if (paused) return remainingWhenPaused;

        Duration remaining = Duration.between(Instant.now(), endTime);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }

    /**
     * Checks whether the countdown has reached zero.
     *
     * @return true if the timer has expired, false otherwise
     */
    public boolean isTimeUp() {
        return started && !paused && Instant.now().isAfter(endTime);
    }
}