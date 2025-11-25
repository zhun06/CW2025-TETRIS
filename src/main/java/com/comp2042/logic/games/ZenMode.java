package com.comp2042.logic.games;

import com.comp2042.util.GameState;

/**
 * Zen mode: focuses on long-form play with incremental level increases
 * based on the player's score. Gravity accelerates as levels rise.
 */
public class ZenMode implements GameMode {

    private final TetrisGame game;
    private int currentLevel = 1;

    /**
     * Creates a ZenMode instance associated with a running Tetris game.
     * @param game the TetrisGame instance
     */
    public ZenMode(TetrisGame game) {this.game = game;}

    /**
     * Updates the level and fall speed based on score
     * and triggers level-up effects when appropriate.
     */
    @Override
    public void onTick() {
        int score = game.getScore().scoreProperty().getValue();
        int newLevel = 1 + score / 1000;

        if (newLevel > currentLevel) {
            currentLevel = newLevel;
            game.onLevelUp();
        }

        int newSpeed = Math.max(150, 400 - currentLevel * 30);
        game.getScore().setLevel(currentLevel);
        game.setFallSpeed(newSpeed);
    }

    /**Ends the game when the board becomes full.*/
    @Override
    public void onBoardFull() {game.setGameState(GameState.GAME_OVER);}
}
