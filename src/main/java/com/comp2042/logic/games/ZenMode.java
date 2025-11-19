package com.comp2042.logic.games;

import com.comp2042.util.GameState;

// Increase speed every level
public class ZenMode implements GameMode {
    private final TetrisGame game;
    private int currentLevel = 1;

    // Constructor
    public ZenMode(TetrisGame game) {this.game = game;}

    @Override
    public void onTick() {
        int score = game.getScore().scoreProperty().getValue();

        int newLevel = 1 +  score / 1000;
        if (newLevel > currentLevel) {
            currentLevel = newLevel;
            game.onLevelUp();
        }
        int newSpeed = Math.max(150, 400 - currentLevel * 30);

        game.getScore().setLevel(currentLevel);
        game.setFallSpeed(newSpeed);
    }

    @Override
    public void onBoardFull() {
        game.setGameState(GameState.GAME_OVER);
    }

}
