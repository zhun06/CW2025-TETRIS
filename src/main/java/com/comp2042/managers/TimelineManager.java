package com.comp2042.managers;

import com.comp2042.engines.TetrisEngine;
import com.comp2042.util.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

// Start, pause, resume, stop
public class TimelineManager {
    // Engine
    TetrisEngine engine;
    // Timeline
    private Timeline boardTimeline;
    // Animation timer
    private AnimationTimer tetrisLoop;

    public void initialize(TetrisEngine engine) {
        this.engine = engine;
        tetrisLoop = engine.getGameLoop();
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
        tetrisLoop.start();
    }

    private void onPause() {
        boardTimeline.pause();
        tetrisLoop.stop();
    }

    private void onUpdate() {
        boardTimeline.play();
    }

    private void onGameOverOrExitOrRestart() {
        boardTimeline.stop();
        tetrisLoop.stop();
    }

}