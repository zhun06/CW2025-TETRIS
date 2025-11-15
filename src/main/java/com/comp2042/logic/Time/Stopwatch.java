package com.comp2042.logic.Time;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch {
    private Instant startTime;
    private Duration accumulated = Duration.ZERO;
    private boolean running = false;

    public void start() {
        accumulated = Duration.ZERO;
        startTime = Instant.now();
        running = true;
    }

    public void pause() {
        if (!running) return;

        accumulated = accumulated.plus(Duration.between(startTime, Instant.now()));
        running = false;
        startTime = null;
    }

    public void resume() {
        if (running) return;

        startTime = Instant.now();
        running = true;
    }

    public Duration getElapsed() {
        if (running) {
            return accumulated.plus(Duration.between(startTime, Instant.now()));
        } else {
            return accumulated;
        }
    }
}


