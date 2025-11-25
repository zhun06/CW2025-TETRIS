package com.comp2042.logic.data;

import com.comp2042.logic.time.*;

import java.time.Duration;

/**
 * Manages countdown timer and stopwatch behavior for a game.
 * Combines both a {@link Timer} and a {@link Stopwatch}.
 */
public final class TimeData {

    private final Timer timer; // Count down

    private final Stopwatch stopwatch; // Count up

    /**
     * @param timer countdown timer
     * @param stopwatch count-up stopwatch
     */
    public TimeData(Timer timer, Stopwatch stopwatch) {
        this.timer = timer;
        this.stopwatch = stopwatch;
    }

    /** Pauses both timer and stopwatch. */
    public void onPause() {
        timer.pause();
        stopwatch.pause();
    }

    /** Resumes both timer and stopwatch. */
    public void onResume() {
        timer.resume();
        stopwatch.resume();
    }

    // Timer methods
    public void startTimer(Duration duration) {timer.start(duration);}
    public Duration getRemainingTime() {return timer.getRemaining();}

    // Stopwatch methods
    public void startStopwatch() {stopwatch.start();}
    public Duration getElapsedTime() {return stopwatch.getElapsed();}
    public boolean isTimeUp() { return timer.isTimeUp();}

}
