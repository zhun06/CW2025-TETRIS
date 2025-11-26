package com.comp2042.logic.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleBoardMovementTest {

    @Test
    void testMoveRight() {
        SimpleBoard board = new SimpleBoard(20, 10);
        board.newGame();

        // start at x = 0, far left
        board.setBrickOffset(0, 0);

        board.moveBrickRight();
        board.moveBrickRight();

        int x = board.getViewData().getxPosition();
        assertEquals(2, x);
    }

    @Test
    void testMoveLeft() {
        SimpleBoard board = new SimpleBoard(20, 10);
        board.newGame();

        // start at x = 4, middle
        board.setBrickOffset(4, 0);

        board.moveBrickLeft();
        board.moveBrickLeft();

        int x = board.getViewData().getxPosition();
        assertEquals(2, x);
    }

    @Test
    void testMoveDown() {
        SimpleBoard board = new SimpleBoard(20, 10);
        board.newGame();

        // start at y = 0, top
        board.setBrickOffset(0, 0);

        boolean m1 = board.moveBrickDown();
        boolean m2 = board.moveBrickDown();

        int y = board.getViewData().getyPosition();

        assertTrue(m1);
        assertTrue(m2);
        assertEquals(2, y);
    }

    @Test
    void testHardDrop() {
        SimpleBoard board = new SimpleBoard(20, 10);
        board.newGame();

        // start at y = 0, top
        board.setBrickOffset(5, 0);
        int before = board.getViewData().getyPosition();

        int ghost = board.getGhostY();
        board.hardDropBrick();
        int after = board.getViewData().getyPosition();

        assertTrue(after > before); // y position of brick must be lesser after hard drop
        assertEquals(ghost, after); // y position of ghost and brick after hard drop should be equal
    }

    @Test
    void testMergeBrickToBackground() {
        SimpleBoard board = new SimpleBoard(20, 10);
        board.newGame(); // empty board

        int[][] before = board.getBoardMatrix();
        int[][] shape = board.getViewData().getBrickShape();
        int offsetX = board.getViewData().getxPosition();
        int offsetY = board.getViewData().getyPosition();

        // Force merge
        board.mergeBrickToBackground();

        int[][] after = board.getBoardMatrix();

        boolean mergedCorrectly = true;

        // Check every cell of the shape matrix against new board
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int boardY = offsetY + r;
                    int boardX = offsetX + c;
                    if (after[boardY][boardX] == before[boardY][boardX]) mergedCorrectly = false;
                }
            }
        }

        assertTrue(mergedCorrectly, "After merging, all brick cells should be written to the board.");
    }

}

