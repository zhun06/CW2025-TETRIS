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

// Survive as long as possible, board rises every 10 seconds
// Mark board as 8 in new rows
public class HardcoreMode implements GameMode {
    private final TetrisGame game;
    private final int fallSpeed = 200;
    private final int interval = 5; // Interval between adding rows to board
    private Duration prevTime;

    // Constructor
    public HardcoreMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getTimeData().startStopwatch();
        prevTime = game.getTimeData().getElapsedTime();
    }

    @Override
    public void onTick() {
        Duration elapsedTime = game.getTimeData().getElapsedTime();
        if (elapsedTime.minus(prevTime).getSeconds() > interval) { // every interval
            prevTime = elapsedTime;

            int rows = new Random().nextInt(3) + 1; // add 1-3 rows
            int[][] newRows = this.createNewRows(rows);
            this.addToBoard(rows, newRows);
        }
    }

    @Override
    public void onBoardFull() {
        game.setGameState(GameState.GAME_OVER);
    }

    // To be added to board
    private int[][] createNewRows(int rows) {
        int[][] newRows = new int[rows][TetrisGame.COLS];

        for (int i = 0; i < rows; i++) {
            Arrays.fill(newRows[i], 8); // Initialize new rows as 8
            List<Integer> emptyCells = getEmptyCells(); // Get random empty cells
            for (Integer emptyCell : emptyCells) {
                newRows[i][emptyCell] = 0; // Mark empty cells as 0
            }
        }
        return newRows;
    }

    // Get 3 random  empty cells for each row
    private List<Integer> getEmptyCells() {
        List<Integer> numbers = IntStream.range(0, TetrisGame.COLS).boxed().collect(Collectors.toList());  // put in list
        Collections.shuffle(numbers); // shuffle
        return numbers.subList(0, 3); // get first 3
    }

    // Add new rows to board
    private void addToBoard(int rows, int[][] newRows) {
        int[][] currentGameMatrix = MatrixOperations.copy(game.getBoard().getBoardMatrix());
        int[][] newGameMatrix = new int[TetrisGame.ROWS][TetrisGame.COLS];

        // Copy shifted current into new matrix
        for (int i = 0; i < TetrisGame.ROWS - rows; i++) {
            System.arraycopy(currentGameMatrix[i+rows], 0, newGameMatrix[i], 0, TetrisGame.COLS);
        }
        System.out.println("Copying shifted matrix: ");
        printMatrix(newGameMatrix);

        System.out.println("New rows to be added: ");
        printMatrix(newRows);
        // Add new rows into new matrix
        for (int i = TetrisGame.ROWS - rows; i < TetrisGame.ROWS; i++) {
            int j = i - TetrisGame.ROWS + rows;
            System.arraycopy(newRows[j], 0, newGameMatrix[i], 0, TetrisGame.COLS);
        }

        // Update current matrix
        game.getBoard().setBoardMatrix(newGameMatrix);
        System.out.println("Updated board matrix: ");
        printMatrix(game.getBoard().getBoardMatrix());

    }

    // Debug
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

}

