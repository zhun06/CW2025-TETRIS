package com.comp2042.logic.games;

import com.comp2042.util.GameState;

/**
 * 40 Lines mode: clear a total of 40 lines as quickly
 * as possible. The game tracks the remaining lines
 * and uses a stopwatch to measure the completion time.
 */
public class FortyMode implements GameMode {
    private final TetrisGame game;
    private final int fallSpeed = 200;
    private final int requiredRows = 40;

    /**
     * Constructs a FortyMode instance for a Tetris game.
     * @param game the TetrisGame instance
     */
    public FortyMode(TetrisGame game) {this.game = game;}

    /** Initializes stopwatch timing and sets fall speed*/
    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getScore().setRowsRemaining(requiredRows);
        game.getTimeData().startStopwatch();
    }

    /** Check rows remaining and update score
     * Ends the game iff 40 rows cleared.
     */
    @Override
    public void onLineClear() {
        int cleared = game.getScore().rowsClearedProperty().getValue();
        int remaining = requiredRows - cleared;

        game.getScore().setRowsRemaining(Math.max(0,remaining));

        if (remaining <= 0) {
            game.getScore().rowsClearedProperty().setValue(requiredRows);
            game.setGameState(GameState.WIN);
            game.onGameOver();
        }

    }

    /**Ends the game if the board becomes full.*/
    @Override
    public void onBoardFull() {
        game.setGameState(GameState.LOSE);
    }

}
