package com.comp2042.logic.games;

import com.comp2042.util.GameState;

import java.time.Duration;

public class BlitzMode implements GameMode{
    private TetrisGame game;
    private final int requiredRows = 10;

    // Constructor
    public BlitzMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.startTimer(Duration.ofMinutes(2));
    }

    @Override
    public void onTick() {
        if (game.isTimeUp()) {
            if (game.getScore().rowsClearedProperty().getValue() >= requiredRows) {
                game.setResult(GameState.WIN);
            }
            else  {
                game.setResult(GameState.LOSE);
            }
            game.setGameOver(true);
        }
    }

}
