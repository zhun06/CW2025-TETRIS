package com.comp2042.logic.moves;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.data.NextShapeInfo;

/**
 * Handles rotation logic for a Tetris {@link Brick}.
 * <p>
 * This class keeps track of the current rotation index and provides
 * the next rotated shape along with predefined kick offsets that can be
 * used by the game engine to resolve collisions after rotation.
 * </p>
 */
public class BrickRotator {

    /** The brick currently being rotated. */
    private Brick brick;

    /** Index of the brick's current rotation state. */
    private int currentShape = 0;

    /**
     * Kick offsets applied when rotating.
     * <p>
     * These offsets are tested in order to find a valid rotation position
     * when the default rotated location would cause a collision.
     * </p>
     * Format: {dx, dy}
     */
    public int[][] KICK_OFFSETS = {
            {0, 0},   // original
            {1, 0},   // right
            {-1, 0},  // left
            {0, -1},  // up
            {2, 0},   // far right
            {-2, 0}   // far left
    };

    /**
     * Returns information about the next rotation state for this brick.
     *
     * @return a {@link NextShapeInfo} containing the next shape matrix and index
     */
    public NextShapeInfo getNextShape() {
        int nextShape = (++currentShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the matrix of the brick's current shape.
     *
     * @return the current rotation matrix
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current rotation index.
     *
     * @param currentShape the new rotation index
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Assigns a new brick to this rotator and resets its rotation state.
     *
     * @param brick the brick to manage
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        this.currentShape = 0;
    }
}
