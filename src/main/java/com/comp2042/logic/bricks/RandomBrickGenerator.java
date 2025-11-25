package com.comp2042.logic.bricks;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A brick generator that randomly selects bricks from a predefined list.
 * Maintains a preview queue of upcoming bricks.
 */
public class RandomBrickGenerator implements BrickGenerator {

    /** Number of bricks shown in the preview window. */
    private final int preview_size = 3;

    private final List<Brick> brickList;
    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Constructs a random brick generator with all seven Tetris bricks.
     * Pre-fills the preview queue to the required size.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());

        while (nextBricks.size() <= preview_size) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
    }

    /**
     * {@inheritDoc}
     * <p>Ensures the preview queue is always filled before returning a brick.</p>
     */
    @Override
    public Brick getBrick() {
        while (nextBricks.size() <= preview_size + 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * {@inheritDoc}
     * <p>Returns a copy of the preview queue to prevent external mutation.</p>
     */
    @Override
    public Queue<Brick> getNextBricks() {
        return new ArrayDeque<>(nextBricks);
    }
}
