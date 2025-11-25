package com.comp2042.logic.games;

import com.comp2042.logic.board.MatrixOperations;
import com.comp2042.util.GameState;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hardcore mode: survival-based gameplay where the board periodically rises.
 * New rows with random holes are added every fixed interval, increasing difficulty.
 */
public class HardcoreMode implements GameMode {
    private final TetrisGame game;
    private final int fallSpeed = 200;
    private final Duration interval = Duration.ofSeconds(10); // Interval between adding rows to board
    private Duration prevTime;
    private final Random random = new Random();

    /**
     * Constructs a HardcoreMode instance for a Tetris game.
     * @param game the TetrisGame instance
     */
    public HardcoreMode(TetrisGame game) {this.game = game;}

    /**Initializes stopwatch timing and adds initial rising rows when the game starts.*/
    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getTimeData().startStopwatch();
        prevTime = game.getTimeData().getElapsedTime();

        int[][] newRows = this.createNewRows(5); // creates 5 new rows
        int[][] newMatrix = this.buildShiftedMatrix(5, newRows); // add new rows to matrix
        game.getBoard().setBoardMatrix(newMatrix); // set new matrix as current
    }

    /**
     * Periodically raises the board by generating and inserting new rows.
     * Also adjusts the current brick's position to avoid collisions.
     */
    @Override
    public void onTick() {
        Duration elapsedTime = game.getTimeData().getElapsedTime();
        if (elapsedTime.minus(prevTime).compareTo(interval) >= 0) { // every interval
            prevTime = elapsedTime;
            game.setGravityLock(true); // lock gravity

            int rows = random.nextInt(10) == 0 ? 2 : 1;  // 10% chance for 2 rows
            int[][] newRows = this.createNewRows(rows); // create rows
            int[][] newMatrix = this.buildShiftedMatrix(rows, newRows); // create new matrix

            // update falling brick position to not intersect with new matrix
            if (updateBrick(newMatrix)) game.getBoard().setBoardMatrix(newMatrix); // set new matrix
            game.setGravityLock(false); // unlock gravity
        }
    }

    /**Ends the game if the board becomes full.*/
    @Override
    public void onBoardFull() {
        game.setGameState(GameState.GAME_OVER);
    }

    // To be added to board
    private int[][] createNewRows(int rows) {
        int[][] newRows = new int[rows][TetrisGame.COLS];

        for (int i = 0; i < rows; i++) {
            Arrays.fill(newRows[i], 8); // initialize new rows as 8
            List<Integer> emptyCells = getEmptyCells(); // get random empty cells
            for (Integer emptyCell : emptyCells) {
                newRows[i][emptyCell] = 0; // mark empty cells as 0
            }
        }
        return newRows;
    }

    // Get random  empty cells for each row
    private List<Integer> getEmptyCells() {
        List<Integer> numbers = IntStream.range(0, TetrisGame.COLS).boxed().collect(Collectors.toList());  // put in list
        Collections.shuffle(numbers); // shuffle
        return numbers.subList(0, 2); // get 2 random cells
    }

    // Build shifted matrix
    private int[][] buildShiftedMatrix(int rows, int[][] newRows) {
        int[][] currentGameMatrix = MatrixOperations.copy(game.getBoard().getBoardMatrix());
        int[][] newGameMatrix = new int[TetrisGame.ROWS][TetrisGame.COLS];

        // Copy shifted current into new matrix
        for (int i = 0; i < TetrisGame.ROWS - rows; i++) {
            System.arraycopy(currentGameMatrix[i+rows], 0, newGameMatrix[i], 0, TetrisGame.COLS);
        }

        // Add new rows into new matrix
        for (int i = 0; i < rows; i++) {
            System.arraycopy(newRows[i], 0, newGameMatrix[TetrisGame.ROWS - rows + i], 0, TetrisGame.COLS);
        }

        return newGameMatrix;
    }

    // Update brick offset
    private boolean updateBrick(int[][] newMatrix) {
        int[][] brick = game.getViewData().getBrickShape();
        int offsetX = game.getViewData().getxPosition();
        int offsetY = game.getViewData().getyPosition();
        while (MatrixOperations.intersect(newMatrix, brick, offsetX, offsetY)) {
            offsetY --; // move brick up
            if (offsetY <= 3) { // out of visible board
                game.onGameOver();
                return false;
            }
        }
        game.getBoard().setBrickOffset(offsetX, offsetY);
        return true;
    }

}

