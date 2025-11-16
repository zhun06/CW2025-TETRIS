package com.comp2042.managers;


import com.comp2042.controllers.GameController;
import com.comp2042.engines.TetrisEngine;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

import java.io.IOException;

// Manage ALL game loop
// MANAGERS created once and REUSED
public class GameManager {
    private static GameChoice currentGameChoice; // Updates
    private static GameState currentGameState; // Updates
    // Game & Engine
    private static TetrisGame game; // Reuse
    private static TetrisEngine engine; // Reuse
    // Game-state managers
    private static GameKeyHandlerManager gameKeyHandlerManager; // Reuse
    private static TimelineManager timelineManager; // Reuse
    private static OverlayManager overlayManager; // Reuse

    private static GameController gameController;

    // Get resources (ONCE)
    public static void initialize(GameController gc) {
        if (game == null) {
            gameController = gc;
            game = new TetrisGame();
            engine = new TetrisEngine(game, gameController);
            gameKeyHandlerManager = new GameKeyHandlerManager();
            timelineManager = new TimelineManager();
            overlayManager = new OverlayManager(gameController);
        }
    }

    public static void startZen() {
        currentGameChoice = GameChoice.ZEN;
        engine.start();
        GameManager.startGame();
    }

    public static void startForty() {
        currentGameChoice = GameChoice.FORTY_LINES;
        engine.start();
        GameManager.startGame();
    }

    public static void startBlitz() {
        currentGameChoice = GameChoice.BLITZ;
        engine.start();
        GameManager.startGame();
    }

    public static void startGame() {
        gameKeyHandlerManager.initialize(game);
        timelineManager.initialize(engine);

        currentGameState = GameState.START;
        timelineManager.update();
        gameKeyHandlerManager.update();
        GameManager.setFocus();
    }

    public static void updateGame() {
        currentGameState = GameState.UPDATE;
        timelineManager.update();
    }

    public static void pauseGame() {
        currentGameState = GameState.PAUSE;
        game.pause();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
    }

    public static void resumeGame() throws IOException{
        currentGameState = GameState.RESUME;
        game.resume();
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        GameManager.setFocus();
    }

    public static void restartGame() {
        currentGameState = GameState.RESTART;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        GameManager.setFocus();

        switch (currentGameChoice) {
            case ZEN -> GameManager.startZen();
            case FORTY_LINES -> GameManager.startForty();
            case BLITZ -> GameManager.startBlitz();
        }
    }

    public static void changeTheme() throws IOException {
        currentGameState = GameState.CHANGE_THEME;
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callThemeController();
    }


    public static void exitGame() throws IOException {
        currentGameState = GameState.EXIT;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
        ControllerManager.callHomeController();
    }

    private static void runOnGameOver() {
        currentGameState = GameState.GAME_OVER;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.update();
    }

    // Notify managers when game over
    public static Runnable setOnGameOver() {return GameManager::runOnGameOver;}


    // Getter
    public static GameChoice getCurrentGameChoice() {return currentGameChoice;}
    public static GameState getCurrentGameState() {return currentGameState;}
    public static GameState getResultState() {return game.getResult();}

    // Set focus
    private static void setFocus() {gameController.getGameBoard().requestFocus();}
}
