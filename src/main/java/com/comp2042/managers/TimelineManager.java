package com.comp2042.managers;

import com.comp2042.engine.TetrisEngine;
import com.comp2042.util.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

/**Manages game timeline and animation based on game state.*/
public class TimelineManager {
    private final TetrisEngine engine;
    private Timeline boardTimeline;
    private AnimationTimer gameLoop;

    /** Construct an instance with {@param engine}*/
    public TimelineManager(TetrisEngine engine) {this.engine = engine;}

    /** Initializes the game loop. */
    public void initialize() { gameLoop = engine.getGameLoop(); }

    /** Updates timeline state based on current game state. */
    public void update() {
        boardTimeline = engine.getBoardTimeLine();
        GameState gameState = GameManager.getCurrentGameState();
        switch (gameState) {
            case START, RESUME -> this.onStartOrResume();
            case PAUSE -> this.onPause();
            case UPDATE -> this.onUpdate();
            case GAME_OVER, EXIT, RESTART -> this.onGameOverOrExitOrRestart();
        }
    }

    private void onStartOrResume() { boardTimeline.play(); gameLoop.start(); }
    private void onPause() { boardTimeline.pause(); gameLoop.stop(); }
    private void onUpdate() { boardTimeline.play(); }
    private void onGameOverOrExitOrRestart() { boardTimeline.stop(); gameLoop.stop(); }
}