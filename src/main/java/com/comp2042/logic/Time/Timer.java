package com.comp2042.logic.Time;

import java.time.Duration;
import java.time.Instant;

public class Timer {
    private Instant endTime;
    private Duration remainingWhenPaused = Duration.ZERO;
    private boolean paused = false;
    private boolean started = false;

    public void start(Duration duration) {
        started = true;
        paused = false;
        endTime = Instant.now().plus(duration);
        remainingWhenPaused = Duration.ZERO;
    }

    public void pause() {
        if (!started || paused) return;

        remainingWhenPaused = Duration.between(Instant.now(), endTime);
        paused = true;
    }

    public void resume() {
        if (!started || !paused) return;

        endTime = Instant.now().plus(remainingWhenPaused);
        paused = false;
    }

    public Duration getRemaining() {
        if (!started) return Duration.ZERO;

        if (paused) return remainingWhenPaused;

        Duration remaining = Duration.between(Instant.now(), endTime);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }

    public boolean isTimeUp() {
        return started && !paused && Instant.now().isAfter(endTime);
    }
}
