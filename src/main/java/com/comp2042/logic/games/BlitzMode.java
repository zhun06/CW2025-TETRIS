package com.comp2042.logic.games;

import com.comp2042.util.GameState;

import java.time.Duration;

// 60 seconds to clear as many rows as possible
public class BlitzMode implements GameMode {
    private final TetrisGame game;
    private final int fallSpeed = 150;

    // Constructor
    public BlitzMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getTimeData().startTimer(Duration.ofSeconds(60));
    }

    @Override
    public void onTick() {
        if (game.getTimeData().isTimeUp()) {
            game.setGameState(GameState.GAME_OVER);
            game.onGameOver();
        }
    }

    @Override
    public void onBoardFull() {
        game.setGameState(GameState.GAME_OVER);
    }

}
