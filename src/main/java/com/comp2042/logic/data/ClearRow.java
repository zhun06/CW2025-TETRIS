package com.comp2042.logic.data;

import com.comp2042.logic.board.MatrixOperations;

import java.util.List;

public final class ClearRow {

    private final int linesRemoved;
    private final List<Integer> clearedRows;
    private final int[][] newMatrix;

    public ClearRow(int linesRemoved, List<Integer> clearedRows, int[][] newMatrix) {
        this.linesRemoved = linesRemoved;
        this.clearedRows = clearedRows;
        this.newMatrix = newMatrix;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public List<Integer> getClearedRows() {return clearedRows;}

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

}
