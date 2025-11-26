package com.comp2042.logic.bricks;

import com.comp2042.logic.board.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**Represents the I-shaped Tetris brick*/
public final class ZBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**{@inheritDoc}*/
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

    /**{@inheritDoc}*/
    @Override
    public int getColor() {return 7;}
}
