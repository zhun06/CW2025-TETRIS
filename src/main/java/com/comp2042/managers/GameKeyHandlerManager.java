package com.comp2042.managers;


import com.comp2042.controllers.GameController;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.util.EventSource;
import com.comp2042.util.EventType;
import com.comp2042.util.GameState;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


// Add or remove event handlers based on game state
public class GameKeyHandlerManager {
    // Boss
    private final GameManager gameManager;
    // Game
    TetrisGame tetrisGame;
    // Game root
    private final Parent gameRoot;
    // Event handlers
    private EventHandler<KeyEvent> gameHandler;
    private EventHandler<KeyEvent> onPauseHandler, onGameOverHandler; // Generic handlers

    // Constructor
    public GameKeyHandlerManager(GameManager gameManager, TetrisGame game, GameController gameController) {
        this.gameManager = gameManager;
        this.tetrisGame = game;
        this.gameRoot = gameController.root;
        this.setOnPauseHandler();
        this.setOnGameOverHandler();
        this.setGameHandler(tetrisGame);
    }

    // Change keys based on game state
    public void update() {
        GameState state = GameManager.getCurrentGameState();

        switch (state) {
            case START -> this.onStart();
            case PAUSE -> this.onPause();
            case RESUME -> this.onResume();
            case RESTART, EXIT -> this.onRestartOrExit();
            case GAME_OVER -> this.onGameOver();
            case LEADER_BOARD -> this.onLeaderBoard();
        }
    }

    // In Game
    public void onStart() {
        this.attachGameHandler();
    }

    // Pause Game
    public void onPause() {
        this.detachGameHandler();
        this.attachHandler(onPauseHandler);
    }

    // Resume Game
    public void onResume() {
        this.detachHandler(onPauseHandler);
        this.attachGameHandler();
    }

    // Restart Game || Exit || Game Over
    public void onRestartOrExit() {
        this.detachGameHandler();
        this.detachHandler(onPauseHandler);
        this.detachHandler(onGameOverHandler);
    }

    public void onGameOver() {
        this.detachGameHandler();
        this.attachHandler(onGameOverHandler);
    }

    // Change Theme
    public void onLeaderBoard() {this.detachHandler(onGameOverHandler);}

    // Generic key handlers
    public void attachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    public void detachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    // In game key handler
    public void attachGameHandler() {gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, gameHandler);}

    public void detachGameHandler() {gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, gameHandler);}

    public void setGameHandler(TetrisGame tetrisGame) {
        gameHandler = event -> {
            switch (event.getCode()) {
                case UP, W -> {
                    tetrisGame.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
                    event.consume();
                }
                case DOWN, S -> {
                    tetrisGame.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
                    event.consume();
                }
                case LEFT, A -> {
                    tetrisGame.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
                    event.consume();
                }
                case RIGHT, D -> {
                    tetrisGame.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
                    event.consume();
                }
                case SPACE, TAB -> {
                    tetrisGame.onHardDropEvent(new MoveEvent(EventType.HARD_DROP, EventSource.USER));
                    event.consume();
                }

                case P -> gameManager.pauseGame();
                case R, N-> gameManager.restartGame();
                case E, Q -> {
                    try {
                        gameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void setOnPauseHandler() {
        this.onPauseHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, P -> {
                    try {
                        gameManager.resumeGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case R, N -> gameManager.restartGame();
                case E, Q -> {
                    try {
                        gameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void setOnGameOverHandler() {
        this.onGameOverHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, ENTER, R, P, N -> gameManager.restartGame();
                case L -> {
                    try {
                        gameManager.viewLeaderBoard();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case E, Q -> {
                    try {
                        gameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

}
