package com.comp2042.logic.bricks;

import com.comp2042.logic.board.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**Represents the S-shaped Tetris brick*/
final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
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
    public int getColor() {return 5;}
}
