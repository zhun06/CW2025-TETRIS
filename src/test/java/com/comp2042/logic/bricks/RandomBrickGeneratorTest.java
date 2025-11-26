package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RandomBrickGeneratorTest {
    @Test
    void getBrick_returnsNonNullBrick() {
        RandomBrickGenerator gen = new RandomBrickGenerator();
        Brick b = gen.getBrick();
        assertNotNull(b);
    }

    @Test
    void getNextBricks_returnsQueueWithExpectedSize() {
        RandomBrickGenerator gen = new RandomBrickGenerator();
        Queue<Brick> queue = gen.getNextBricks();
        assertEquals(3, queue.size()); // preview size
    }

    @Test
    void getBrick_refillsQueue() {
        RandomBrickGenerator gen = new RandomBrickGenerator();
        int initialSize = gen.getNextBricks().size();
        gen.getBrick();
        int newSize = gen.getNextBricks().size();
        assertEquals(newSize, initialSize); // queue is refilled
    }

}