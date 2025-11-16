package com.comp2042.logic.games;

import com.comp2042.util.GameState;

// Clear 40 rows as fast as possible
public class FortyMode implements GameMode{
    private final TetrisGame game;
    private final int requiredRows = 40;

    // Constructor
    public FortyMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.getScore().setRowsRemaining(requiredRows);
        game.startStopwatch();
    }

    @Override
    public void onLineClear() {
        int rowsCleared = game.getScore().rowsClearedProperty().getValue();
        game.getScore().setRowsRemaining(requiredRows - rowsCleared);
        if (rowsCleared >= requiredRows) {
            game.setResult(GameState.WIN);
            game.setGameOver(true);
        }
    }

    @Override
    public void onBoardFull() {game.setResult(GameState.LOSE);}

}
