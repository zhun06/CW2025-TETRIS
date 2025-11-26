package com.comp2042.logic.board;

import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.games.TetrisGame;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {
    // Intersect
    @Test
    void intersect_returnsTrue_whenCollision() {
        int[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        };
        int[][] brick = {
                {1, 0},
                {0, 0}
        };
        assertTrue(MatrixOperations.intersect(board, brick, 0, 0)); // no offset
        assertTrue(MatrixOperations.intersect(board, brick, 1, 1)); // offset x and y by 1
        assertTrue(MatrixOperations.intersect(board, brick, 2, 2)); // offset x and y by 2
    }

    @Test
    void intersect_returnsFalse_whenNoCollision() {
        int[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        };
        int[][] brick = {
                {1, 0},
                {0, 1}
        };
        assertFalse(MatrixOperations.intersect(board, brick, 1, 0)); // offset x by 1
        assertFalse(MatrixOperations.intersect(board, brick, 0, 1)); // offset y by 1
    }

    @Test
    void intersect_returnsTrue_whenOutOfBounds() {
        int[][] board = new int[2][2];
        int[][] brick = {{1}};
        // negative x
        assertTrue(MatrixOperations.intersect(board, brick, -1, 0));
        // negative y
        assertTrue(MatrixOperations.intersect(board, brick, 0, -1));
        // beyond width
        assertTrue(MatrixOperations.intersect(board, brick, TetrisGame.COLS, 0));
        // beyond height
        assertTrue(MatrixOperations.intersect(board, brick, 0, TetrisGame.ROWS));
    }

    // Copy
    @Test
    void copy_createsIndependentMatrix() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copy = MatrixOperations.copy(original);
        assertArrayEquals(original, copy);
        // modify copy, original should remain unchanged
        copy[0][0] = 99;
        assertEquals(1, original[0][0]);
    }

    @Test
    void copy_handlesEmptyMatrix() {
        int[][] original = new int[0][0];
        int[][] copy = MatrixOperations.copy(original);
        assertEquals(0, copy.length);
    }

    // Merge
    @Test
    void merge_addsBrickCorrectly() {
        int[][] board = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        int[][] brick = {
                {1, 2},
                {3, 0}
        };
        int[][] merged1 = MatrixOperations.merge(board, brick, 0, 0); // no offset
        int[][] expected1 = {
                {1, 2, 0},
                {3, 0, 0},
                {0, 0, 0}
        };
        int[][] merged2 = MatrixOperations.merge(board, brick, 1, 0); // offset x by 1
        int[][] expected2 = {
                {0, 1, 2},
                {0, 3, 0},
                {0, 0, 0}
        };
        int[][] merged3 = MatrixOperations.merge(board, brick, 0, 1); // offset y by 1
        int[][] expected3 = {
                {0, 0, 0},
                {1, 2, 0},
                {3, 0, 0}
        };
        assertArrayEquals(expected1, merged1);
        assertArrayEquals(expected2, merged2);
        assertArrayEquals(expected3, merged3);
    }

    @Test
    void merge_doesNotAffectOriginal() {
        int[][] board = {{0,0},{0,0}};
        int[][] brick = {{1}};
        int[][] merged = MatrixOperations.merge(board, brick, 0, 0);
        assertArrayEquals(new int[][]{{0,0},{0,0}}, board); // original intact
    }

    // Check removing
    @Test
    void checkRemoving_clearsAll() {
        int[][] board = {
                {1, 1, 5},
                {2, 2, 3},
                {3, 8, 3}
        };
        int[][] expected = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(3, result.getLinesRemoved());
        assertEquals(List.of(0, 1, 2), result.getClearedRows());
        assertArrayEquals(expected, result.getNewMatrix());
    }


    @Test
    void checkRemoving_partialClear() {
        int[][] board = {
                {1, 0, 5},
                {2, 2, 3},
                {3, 8, 3}
        };
        int[][] expected = { // Board shifts down
                {0, 0, 0},
                {0, 0, 0},
                {1, 0, 5}
        };
        ClearRow result = MatrixOperations.checkRemoving(board);
        assertEquals(2, result.getLinesRemoved());
        assertEquals(List.of(1, 2), result.getClearedRows());
        assertArrayEquals(expected, result.getNewMatrix());
    }

    @Test
    void checkRemoving_noClear() {
        int[][] board = {
                {1, 1, 0},
                {2, 0, 2},
                {0, 3, 3}
        };
        ClearRow result = MatrixOperations.checkRemoving(board);
        assertEquals(0, result.getLinesRemoved());
        assertTrue(result.getClearedRows().isEmpty());
        assertArrayEquals(board, result.getNewMatrix());
    }

    // Deep copy list
    @Test
    void deepCopyList_createsIndependentCopies() {
        int[][] matrix1 = {{1}};
        int[][] matrix2 = {{2}};
        List<int[][]> originalList = new ArrayList<>(List.of(matrix1, matrix2));
        List<int[][]> copyList = MatrixOperations.deepCopyList(originalList);

        // list size same
        assertEquals(originalList.size(), copyList.size());
        // matrices content same
        assertArrayEquals(originalList.get(0), copyList.get(0));
        assertArrayEquals(originalList.get(1), copyList.get(1));

        // modifying copy should not affect original
        copyList.get(0)[0][0] = 99;
        assertEquals(1, originalList.get(0)[0][0]);
    }
}