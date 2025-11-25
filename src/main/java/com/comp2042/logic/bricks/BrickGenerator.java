package com.comp2042.logic.bricks;

import java.util.Queue;

/**Generates Tetris bricks and provides a preview of upcoming bricks.*/
public interface BrickGenerator {
    /**
     * Returns the next brick to be spawned on the board.
     * @return the next {@link Brick}
     */
    Brick getBrick();

    /**
     * Returns a queue representing upcoming bricks (preview window).
     * Implementations should return a copy rather than exposing
     * internal mutable state.
     * @return a queue of upcoming {@link Brick} objects
     */
    Queue<Brick> getNextBricks();
}