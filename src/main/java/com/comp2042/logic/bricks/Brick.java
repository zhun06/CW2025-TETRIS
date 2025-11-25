package com.comp2042.logic.bricks;

import java.util.List;

/**Represents a Tetris brick with one or more rotation states.*/
public interface Brick {
    /**
     * Returns all rotation states of this brick.
     * Each element is a 2D matrix representing the brick layout.
     * @return a list of 2D arrays containing the brick's rotation matrices
     */
    List<int[][]> getShapeMatrix();

    /**
     * Returns the color code used for rendering this brick.
     * @return an integer representing the brick color
     */
    int getColor();
}

