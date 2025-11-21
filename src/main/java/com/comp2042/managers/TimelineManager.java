package com.comp2042.managers;

import com.comp2042.engine.TetrisEngine;
import com.comp2042.util.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

// Start, pause, resume, stop
public class TimelineManager {
    // Boss
    GameManager gameManager;
    // Engine
    TetrisEngine engine;
    // Timeline
    private Timeline boardTimeline;
    // Animation timer
    private AnimationTimer gameLoop;

    public TimelineManager(GameManager gameManager, TetrisEngine engine) {
        this.gameManager = gameManager;
        this.engine = engine;
    }

    public void initialize() {
        gameLoop = engine.getGameLoop();
    }

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

    private void onStartOrResume() {
        boardTimeline.play();
        gameLoop.start();
    }

    private void onPause() {
        boardTimeline.pause();
        gameLoop.stop();
    }

    private void onUpdate() {
        boardTimeline.play();
    }

    private void onGameOverOrExitOrRestart() {
        boardTimeline.stop();
        gameLoop.stop();
    }

}