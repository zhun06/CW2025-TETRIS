package com.comp2042.managers;


import com.comp2042.controllers.GameController;
import com.comp2042.engine.TetrisEngine;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.sfx.SoundLoader;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;

import java.io.IOException;

// Singleton class
// Manage ALL game loop
// ALL MANAGERS created once and REUSED
// Game and engine created once and REUSED
public class GameManager {
    // Instance of itself
    private static GameManager instance;
    // Game & Engine
    private final TetrisGame game; // Reuse
    private final TetrisEngine engine; // Reuse
    // Game-state managers (workers)
    private final GameKeyHandlerManager gameKeyHandlerManager; // Reuse
    private final TimelineManager timelineManager; // Reuse
    private final OverlayManager overlayManager; // Reuse
    // Game state (global)
    private static GameChoice currentGameChoice; // Updates
    private static GameState currentGameState; // Updates

    private final GameController gameController;

    private GameManager(GameController gameController) {
        this.gameController = gameController;
        this.game = new TetrisGame();
        this.engine = new TetrisEngine(this, game, gameController);
        this.gameKeyHandlerManager = new GameKeyHandlerManager(this, game, gameController);
        this.timelineManager = new TimelineManager(this, engine);
        this.overlayManager = new OverlayManager(this, gameController);
    }

    public static GameManager getInstance(GameController gameController) {
        if (instance == null) {
            instance = new GameManager(gameController);
        }
        return instance;
    }

    public void startGame(GameChoice gameChoice) {
        currentGameState = GameState.START;
        currentGameChoice = gameChoice;
        engine.start();
        timelineManager.initialize();
        timelineManager.update();
        gameKeyHandlerManager.update();
        this.setFocus();
        SoundLoader.playMusic();
    }

    public void updateGame() {
        currentGameState = GameState.UPDATE;
        timelineManager.update();
    }

    public void pauseGame() {
        currentGameState = GameState.PAUSE;
        game.pause();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        SoundLoader.pauseMusic();
    }

    public void resumeGame() throws IOException{
        currentGameState = GameState.RESUME;
        game.resume();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        this.setFocus();
        SoundLoader.playMusic();
    }

    public void restartGame() {
        currentGameState = GameState.RESTART;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        this.setFocus();
        SoundLoader.stopMusic();

        this.startGame(currentGameChoice);
    }

    public void viewLeaderBoard() throws IOException {
        currentGameState = GameState.LEADER_BOARD;
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callLeaderBoardController();
    }


    public void exitGame() throws IOException {
        currentGameState = GameState.EXIT;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callHomeController();
        SoundLoader.stopMusic();
    }

    private void runOnGameOver() {
        currentGameState = GameState.GAME_OVER;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        SoundLoader.stopMusic();
    }

    // Notify managers when game over
    public Runnable setOnGameOver() {return this::runOnGameOver;}


    // Getter
    public static GameChoice getCurrentGameChoice() {return currentGameChoice;}
    public static GameState getCurrentGameState() {return currentGameState;}

    // Set focus
    private void setFocus() {gameController.getGameBoard().requestFocus();}
}
