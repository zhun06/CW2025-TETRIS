package com.comp2042.logic.games;

import com.comp2042.util.GameState;

// Clear 40 rows as fast as possible
public class FortyMode implements GameMode {
    private final TetrisGame game;
    private final int fallSpeed = 200;
    private final int requiredRows = 40;

    // Constructor
    public FortyMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getScore().setRowsRemaining(requiredRows);
        game.getTimeData().startStopwatch();
    }

    @Override
    public void onLineClear() {
        int remainingRows = requiredRows - game.getScore().rowsClearedProperty().getValue();
        game.getScore().setRowsRemaining(Math.max(0, remainingRows));

        if (remainingRows <= 0) {
            game.getScore().rowsClearedProperty().setValue(requiredRows);
            game.setGameState(GameState.WIN);
            game.onGameOver();
        }
    }

    @Override
    public void onBoardFull() {
        game.setGameState(GameState.LOSE);
    }

}
