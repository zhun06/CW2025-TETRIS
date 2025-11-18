package com.comp2042.logic.games;

import com.comp2042.util.GameState;

// Increase speed every level
public class ZenMode implements GameMode {
    private final TetrisGame game;

    // Constructor
    public ZenMode(TetrisGame game) {this.game = game;}

    @Override
    public void onTick() {
        int score = game.getScoreData().scoreProperty().getValue();
        int level = 1 +  score/ 1000;
        int newSpeed = Math.max(150, 400 - level * 30);

        game.getScoreData().setLevel(level);
        game.setFallSpeed(newSpeed);
    }

    @Override
    public void onBoardFull() {
        game.setGameState(GameState.GAME_OVER);
    }

}
