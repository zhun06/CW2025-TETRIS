package com.comp2042.logic.games;

import com.comp2042.util.GameState;

public class ZenMode implements GameMode{
    private final TetrisGame game;

    // Constructor
    public ZenMode(TetrisGame game) {this.game = game;}

    @Override
    public void onLineClear() {
        int score = game.getScore().scoreProperty().getValue();
        int level = 1 +  score/ 3000;
        int newSpeed = Math.max(150, 400 - level * 30);

        game.getScore().setLevel(level);
        game.setFallSpeed(newSpeed);
    }

    @Override
    public void onBoardFull() {game.setResult(GameState.GAME_OVER);}

}
