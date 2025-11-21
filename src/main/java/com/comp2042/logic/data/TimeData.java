package com.comp2042.logic.data;

import com.comp2042.logic.time.*;

import java.time.Duration;

public final class TimeData {

    private final Timer timer; // Count down

    private final Stopwatch stopwatch; // Count up

    // Constructor
    public TimeData(Timer timer, Stopwatch stopwatch) {
        this.timer = timer;
        this.stopwatch = stopwatch;
    }

    // Setters
    public void onPause() {
        timer.pause();
        stopwatch.pause();
    }
    public void onResume() {
        timer.resume();
        stopwatch.resume();
    }

    // Timer
    public void startTimer(Duration duration) {timer.start(duration);}
    public Duration getRemainingTime() {return timer.getRemaining();}

    // Stopwatch
    public void startStopwatch() {stopwatch.start();}
    public Duration getElapsedTime() {return stopwatch.getElapsed();}
    public boolean isTimeUp() { return timer.isTimeUp();}

}
