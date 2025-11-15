package com.comp2042.logic.games;

import com.comp2042.util.GameState;

public class FortyMode implements GameMode{
    private final TetrisGame game;

    // Constructor
    public FortyMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.startStopwatch();
    }

    @Override
    public void onLineClear() {
        int rowsCleared = game.getScore().rowsClearedProperty().getValue();
        if (rowsCleared >= 40) {
            game.setResult(GameState.WIN);
            game.setGameOver(true);
        }
    }

    @Override
    public void onGameOver() {
        game.setResult(GameState.LOSE);
    }


}
