package com.comp2042.logic.time;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    @Test
    void start_setsRemainingTime() {
        Timer timer = new Timer();
        timer.start(Duration.ofMillis(100));
        assertTrue(timer.getRemaining().toMillis() <= 100 && timer.getRemaining().toMillis() >= 95);
    }

    @Test
    void pause_freezesRemainingTime() throws InterruptedException {
        Timer timer = new Timer();
        timer.start(Duration.ofMillis(100));
        timer.pause();
        Thread.sleep(50); // delay
        assertTrue(timer.getRemaining().toMillis() <= 100 && timer.getRemaining().toMillis() >= 95);
    }

    @Test
    void resume_continuesCountdown() throws InterruptedException {
        Timer timer = new Timer();
        timer.start(Duration.ofMillis(100));
        timer.pause();
        Duration paused = timer.getRemaining();
        timer.resume();
        Thread.sleep(50);
        assertTrue(timer.getRemaining().toMillis() < paused.toMillis());
    }

    @Test
    void isTimeUp_detectsExpiration() throws InterruptedException {
        Timer timer = new Timer();
        timer.start(Duration.ofMillis(50));
        Thread.sleep(50);
        assertTrue(timer.isTimeUp());
    }
}