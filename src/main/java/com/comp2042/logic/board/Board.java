package com.comp2042.logic.board;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.Score;
import com.comp2042.logic.data.ViewData;

import java.util.Queue;

import java.util.Queue;

/**
 * Interface representing a Tetris game board.
 * Provides methods to move and rotate bricks, manage the board state,
 * track upcoming bricks, score, and check for game over conditions.
 */
public interface Board {

    /** Moves the current brick down by one row if possible.
     * @return true if the brick moved successfully; false if blocked */
    boolean moveBrickDown();

    /** Moves the current brick left by one column if possible. */
    void moveBrickLeft();

    /** Moves the current brick right by one column if possible. */
    void moveBrickRight();

    /** Rotates the current brick 90 degrees counterclockwise. */
    void rotateLeftBrick();

    /** Drops the current brick to the lowest possible position immediately. */
    void hardDropBrick();

    /** Creates a new brick on the board and updates next bricks queue. */
    void createNewBrick();

    /** Merges the current brick into the board permanently. */
    void mergeBrickToBackground();

    /** Checks for full rows and removes them.
     * @return object representing cleared rows and new matrix */
    ClearRow clearRow();

    /** Replaces the current board matrix with a new one.
     * @param newBoardMatrix the new board state */
    void setBoardMatrix(int[][] newBoardMatrix);

    /** Sets the current brick's offset (position) on the board.
     * @param offsetX horizontal position
     * @param offsetY vertical position */
    void setBrickOffset(int offsetX, int offsetY);

    /** Returns the current board matrix.
     * @return 2D array representing the board */
    int[][] getBoardMatrix();

    /** Returns a queue of upcoming bricks. */
    Queue<Brick> getNextBricks();

    /** Returns data needed to render the board and current brick.
     * @return view data for rendering */
    ViewData getViewData();

    /** Returns the Y position of the "ghost" brick (where it would land). */
    int getGhostY();

    /** Returns the current score of the board. */
    Score getScore();

    /** Checks whether the board is full (game over).
     * @return true if full, false otherwise */
    boolean isFull();

    /** Initializes a new game on this board. */
    void newGame();
}
