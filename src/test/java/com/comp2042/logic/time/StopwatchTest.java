package com.comp2042.logic.time;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StopwatchTest {
    @Test
    void start_resetsElapsed() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        sw.start();
        Thread.sleep(50);
        assertTrue(sw.getElapsed().toMillis() >= 50);
    }

    @Test
    void pause_stopsAccumulation() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        sw.start();
        Thread.sleep(50);
        sw.pause();
        Duration pausedTime = sw.getElapsed();
        Thread.sleep(50);
        assertEquals(pausedTime.toMillis(), sw.getElapsed().toMillis(), 1); // differ by less than 1 ms
    }

    @Test
    void resume_continuesTiming() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        sw.start();
        Thread.sleep(50);
        sw.pause();
        Duration pausedTime = sw.getElapsed();
        sw.resume();
        Thread.sleep(50);
        assertTrue(sw.getElapsed().toMillis() > pausedTime.toMillis());
    }
}