package com.comp2042.managers;

import com.comp2042.engines.TetrisEngine;
import com.comp2042.util.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

// Start, pause, resume, stop
public class TimelineManager {
    private GameState gameState;
    // Timeline
    private Timeline tetrisBoardTimeline;
    // Animation timer
    private AnimationTimer tetrisLoop;

    public void initialize(TetrisEngine engine) {
        tetrisBoardTimeline = engine.getBoardTimeLine();
        tetrisLoop = engine.getGameLoop();
    }

    public void update() {
        gameState = GameManager.getCurrentGameState();

        switch (gameState) {
            case START, RESUME -> this.onStartOrResume();
            case PAUSE -> this.onPause();
            case GAMEOVER, EXIT, RESTART -> this.onGameOverOrExitOrRestart();
        }
    }

    private void onStartOrResume() {
        tetrisBoardTimeline.play();
        tetrisLoop.start();
    }

    private void onPause() {
        tetrisBoardTimeline.pause();
        tetrisLoop.stop();
    }

    private void onGameOverOrExitOrRestart() {
        tetrisBoardTimeline.stop();
        tetrisLoop.stop();
    }

}