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


/**
 * Manages all keyboard input logic for the game.
 * <p>
 * Attaches and detaches key event handlers depending on the current {@link GameState}.
 * Uses separate handlers for in-game controls, pause menu, and game-over interactions.
 * Ensures input behavior always matches the current UI and gameplay context.
 */
public class GameKeyHandlerManager {
    private final GameManager gameManager;
    private final TetrisGame tetrisGame;
    private final Parent gameRoot;
    private EventHandler<KeyEvent> gameHandler;
    private EventHandler<KeyEvent> onPauseHandler, onGameOverHandler;

    /**
     * Constructor.
     * @param gameManager the game manager
     * @param game the Tetris game instance
     * @param gameController the game controller
     */
    public GameKeyHandlerManager(GameManager gameManager, TetrisGame game, GameController gameController) {
        this.gameManager = gameManager;
        this.tetrisGame = game;
        this.gameRoot = gameController.root;
        this.setOnPauseHandler();
        this.setOnGameOverHandler();
        this.setGameHandler(tetrisGame);
    }

    /** Updates key event handlers based on current game state. */
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

    /** Activates game key handler when game starts. */
    public void onStart() { this.attachGameHandler(); }

    /** Attaches pause key handler and detaches game handler. */
    public void onPause() {
        this.detachGameHandler();
        this.attachHandler(onPauseHandler);
    }

    /** Resumes game handler and detaches pause handler. */
    public void onResume() {
        this.detachHandler(onPauseHandler);
        this.attachGameHandler();
    }

    /** Detaches all handlers for restart or exit. */
    public void onRestartOrExit() {
        this.detachGameHandler();
        this.detachHandler(onPauseHandler);
        this.detachHandler(onGameOverHandler);
    }

    /** Activates game-over handler. */
    public void onGameOver() {
        this.detachGameHandler();
        this.attachHandler(onGameOverHandler);
    }

    /** Removes game-over handler when switching to leader board. */
    public void onLeaderBoard() { this.detachHandler(onGameOverHandler); }

    // Generic attach/detach handlers
    public void attachHandler(EventHandler<KeyEvent> handler) { gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, handler); }
    public void detachHandler(EventHandler<KeyEvent> handler) { gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, handler); }
    public void attachGameHandler() { gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, gameHandler); }
    public void detachGameHandler() { gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, gameHandler); }

    /**
     * Sets the in-game key handler mapping keys to game events.
     * @param tetrisGame the Tetris game instance
     */
    public void setGameHandler(TetrisGame tetrisGame) {
        gameHandler = event -> {
            switch (event.getCode()) {
                case UP, W -> { tetrisGame.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)); event.consume(); }
                case DOWN, S -> { tetrisGame.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER)); event.consume(); }
                case LEFT, A -> { tetrisGame.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)); event.consume(); }
                case RIGHT, D -> { tetrisGame.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)); event.consume(); }
                case SPACE, TAB, ENTER -> { tetrisGame.onHardDropEvent(new MoveEvent(EventType.HARD_DROP, EventSource.USER)); event.consume(); }
                case P -> gameManager.pauseGame();
                case R, N -> gameManager.restartGame();
                case E, Q -> { try { gameManager.exitGame(); } catch (IOException e) { throw new RuntimeException(e); } }
            }
        };
    }

    /** Sets key handler for pause state. */
    public void setOnPauseHandler() {
        this.onPauseHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, P -> { try { gameManager.resumeGame(); } catch (IOException e) { throw new RuntimeException(e); } }
                case R, N -> gameManager.restartGame();
                case E, Q -> { try { gameManager.exitGame(); } catch (IOException e) { throw new RuntimeException(e); } }
            }
        };
    }

    /** Sets key handler for game-over state. */
    public void setOnGameOverHandler() {
        this.onGameOverHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, ENTER, R, P, N -> gameManager.restartGame();
                case L -> { try { gameManager.viewLeaderBoard(); } catch (IOException e) { throw new RuntimeException(e); } }
                case E, Q -> { try { gameManager.exitGame(); } catch (IOException e) { throw new RuntimeException(e); } }
            }
        };
    }
}