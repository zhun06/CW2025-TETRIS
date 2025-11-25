package com.comp2042.logic.board;

import com.comp2042.logic.data.ClearRow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing static matrix operations for Tetris.
 * Includes collision detection, deep copy, merging, and row-clearing operations.
 */
public class MatrixOperations {
    /** Prevent instantiation of utility class. */
    private MatrixOperations(){}

    /**
     * Checks whether a brick intersects with a board at a given offset.
     *
     * @param matrix the board matrix
     * @param brick the brick matrix
     * @param x horizontal offset
     * @param y vertical offset
     * @return true if there is a collision or out-of-bounds, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }


    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            return false;
        }
        return true;
    }

    /**
     * Returns a deep copy of a 2D matrix.
     * @param original the matrix to copy
     * @return new 2D array with the same content
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into a board matrix at a given position.
     * @param filledFields the current board matrix
     * @param brick the brick matrix
     * @param x horizontal position
     * @param y vertical position
     * @return a new matrix representing the merged board
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for full rows in a matrix and removes them.
     * @param matrix the board matrix
     * @return a {@link ClearRow} object containing cleared row info and updated matrix
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }

        return new ClearRow(clearedRows.size(), clearedRows, tmp);
    }

    /**
     * Returns a deep copy of a list of matrices.
     * @param list list of 2D matrices
     * @return new list of deep-copied matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
