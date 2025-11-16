package com.comp2042.logic.games;

import com.comp2042.util.GameState;

import java.time.Duration;

// 90 seconds to clear as many rows as possible
public class BlitzMode implements GameMode{
    private final TetrisGame game;

    // Constructor
    public BlitzMode(TetrisGame game) {this.game = game;}

    @Override
    public void onGameStart() {game.startTimer(Duration.ofSeconds(90));}

    @Override
    public void onTick() {
        if (game.isTimeUp()) {
            game.setResult(GameState.GAME_OVER);
            game.setGameOver(true);
        }
    }

    @Override
    public void onBoardFull() {game.setResult(GameState.GAME_OVER);}

}
