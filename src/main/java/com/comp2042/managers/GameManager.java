package com.comp2042.managers;


import com.comp2042.controllers.GameController;
import com.comp2042.engine.TetrisEngine;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.sfx.SoundLoader;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Singleton responsible for managing the entire Tetris game lifecycle.
 * <p>
 * Creates and reuses all core game components such as the {@link TetrisGame}, {@link TetrisEngine},
 * {@link GameKeyHandlerManager}, {@link TimelineManager} and {@link OverlayManager}. Handles state transitions such as
 * start, pause, resume, restart, exit, and game over.
 * <p>
 * Only one instance of this class exists, and it is shared across all scenes.
 */
public class GameManager {

    private static GameManager instance;

    private final TetrisGame game;
    private final TetrisEngine engine;
    private final GameKeyHandlerManager gameKeyHandlerManager;
    private final TimelineManager timelineManager;
    private final OverlayManager overlayManager;

    private static GameChoice currentGameChoice;
    private static GameState currentGameState;

    private final GridPane gameBoard;

    /** Private constructor for singleton pattern. */
    private GameManager(GameController gameController) {
        this.gameBoard = gameController.getGameBoard();
        this.game = new TetrisGame();
        this.engine = new TetrisEngine(this, game, gameController);
        this.gameKeyHandlerManager = new GameKeyHandlerManager(this, game, gameController);
        this.timelineManager = new TimelineManager(engine);
        this.overlayManager = new OverlayManager(gameController);
    }

    /**
     * Returns the singleton instance of GameManager.
     * @param gameController the GameController instance
     * @return the GameManager instance
     */
    public static GameManager getInstance(GameController gameController) {
        if (instance == null) {
            instance = new GameManager(gameController);
        }
        return instance;
    }

    /**
     * Starts a new game with the specified game choice.
     * @param gameChoice the selected game mode
     */
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

    /** Updates the game during active play. */
    public void updateGame() {
        currentGameState = GameState.UPDATE;
        timelineManager.update();
    }

    /** Pauses the game and updates all managers. */
    public void pauseGame() {
        currentGameState = GameState.PAUSE;
        game.pause();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        SoundLoader.pauseMusic();
    }

    /** Resumes the game from pause and updates all managers. */
    public void resumeGame() throws IOException{
        currentGameState = GameState.RESUME;
        game.resume();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        this.setFocus();
        SoundLoader.playMusic();
    }

    /** Restarts the current game. */
    public void restartGame() {
        currentGameState = GameState.RESTART;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        this.setFocus();
        SoundLoader.stopMusic();
        this.startGame(currentGameChoice);
    }

    /** Opens the leader board view. */
    public void viewLeaderBoard() throws IOException {
        currentGameState = GameState.LEADER_BOARD;
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callLeaderBoardController();
    }

    /** Exits the current game and returns to home scene. */
    public void exitGame() throws IOException {
        currentGameState = GameState.EXIT;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callHomeController();
        SoundLoader.stopMusic();
    }

    /** Handles game-over state. */
    private void runOnGameOver() {
        currentGameState = GameState.GAME_OVER;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        SoundLoader.stopMusic();
    }

    /**
     * Returns a Runnable to be executed on game over.
     * @return Runnable for game over
     */
    public Runnable setOnGameOver() { return this::runOnGameOver; }

    // Getters
    public static GameChoice getCurrentGameChoice() { return currentGameChoice; }
    public static GameState getCurrentGameState() { return currentGameState; }

    /** Sets focus to the game board. */
    private void setFocus() { gameBoard.requestFocus(); }
}