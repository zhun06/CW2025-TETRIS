package com.comp2042.logic.data;

import com.comp2042.logic.board.MatrixOperations;

/**Represents the information for shape of next rotation .*/
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * @param shape the brick matrix
     * @param position the rotation position of the brick
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**@return a defensive copy of the shape matrix*/
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /** @return  position of rotation */
    public int getPosition() { return position; }
}
