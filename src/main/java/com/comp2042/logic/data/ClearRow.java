package com.comp2042.logic.data;

import com.comp2042.logic.board.MatrixOperations;

import java.util.List;

/**
 * Represents the result of clearing one or more completed lines.
 * Contains the number of lines removed, the indexes of cleared rows,
 * and the resulting updated board matrix.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final List<Integer> clearedRows;
    private final int[][] newMatrix;

    /**
     * Creates a new clear-row result.
     *
     * @param linesRemoved number of lines removed
     * @param clearedRows indexes of the cleared rows
     * @param newMatrix the updated board matrix after clearing
     */
    public ClearRow(int linesRemoved, List<Integer> clearedRows, int[][] newMatrix) {
        this.linesRemoved = linesRemoved;
        this.clearedRows = clearedRows;
        this.newMatrix = newMatrix;
    }

    /** @return number of lines removed */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /** @return indexes of the cleared rows */
    public List<Integer> getClearedRows() { return clearedRows; }

    /**
     * Returns a defensive copy of the updated board matrix.
     *
     * @return a copied board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
