package com.comp2042.logic.games;

public class ZenMode implements GameMode{
    private final TetrisGame game;

    // Constructor
    public ZenMode(TetrisGame game) {this.game = game;}

    @Override
    public void onLineClear() {
        int totalLines = game.getScore().rowsClearedProperty().getValue();
        int level = 1 + totalLines / 10;
        int newSpeed = Math.max(100, 400 - level * 60);

        game.getScore().setLevel(level);
        game.setFallSpeed(newSpeed);
    }

}
