package com.comp2042.logic.bricks;

import com.comp2042.logic.board.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**Represents the O-shaped Tetris brick*/
public final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
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
    public int getColor() {return 4;}
}
