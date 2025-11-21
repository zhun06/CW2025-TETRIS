package com.comp2042.engine;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.managers.GameManager;
import com.comp2042.renderers.*;
import com.comp2042.setters.ResultSetter;
import com.comp2042.setters.StatsSetter;
import com.comp2042.sfx.SfxManager;
import com.comp2042.util.EventSource;
import com.comp2042.util.EventType;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

// Setup timeline and rendering
public class TetrisEngine  {
    // Boss
    GameManager gameManager;
    // Helpers (created once and REUSED)
    private BoardRenderer boardRenderer;
    private PreviewRenderer previewRenderer;
    private StatsSetter statsSetter;
    private ResultSetter resultSetter;
    private SfxManager sfxManager;
    // Timeline
    private Timeline boardTimeLine;
    private AnimationTimer gameLoop;
    private Runnable onGameOver;
    // Flags
    private boolean gameOverPending;
    private boolean gameOverHandled;

    private final TetrisGame game;
    private final GameController gameController;

    private int currentSpeed;

    // Constructor
    public TetrisEngine(GameManager gameManager, TetrisGame game, GameController gameController) {
        this.gameManager = gameManager;
        this.game = game;
        this.gameController = gameController;
        this.initialize();
    }

    // Once
    private void initialize() {
        boardRenderer = new BoardRenderer(gameController, game.getBoard());
        previewRenderer = new PreviewRenderer(gameController);
        statsSetter = new StatsSetter(gameController);
        resultSetter = new ResultSetter(gameController);
        sfxManager = new SfxManager(gameController);
        onGameOver = gameManager.setOnGameOver();
    }

    public void start() {
        game.createNewGame();
        this.onNewGame();
        this.setupBoardTimeline();
        this.setupGameLoop();
        gameOverPending = false;
        gameOverHandled = false;
    }

    private void onNewGame() {
        boardRenderer.onNewGame();
        previewRenderer.onNewGame();
        statsSetter.onNewGame();
        sfxManager.onNewGame();
    }

    private void setupBoardTimeline() {
        currentSpeed = game.getFallSpeed();
        boardTimeLine = new Timeline(new KeyFrame(Duration.millis(currentSpeed), e -> {
            game.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
            this.checkSpeedChange();
        }));
        boardTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkGameOver();
                if (!gameOverPending) {// Freeze game logic
                    boardRenderer.render(game.getViewData());
                    previewRenderer.render(game.getNextBricks());
                }
                // Always update stats and sfx
                statsSetter.update(game.getScore(), game.getTimeData().getRemainingTime(), game.getTimeData().getElapsedTime(), game.getGameResult());
                sfxManager.update(game.getSfxData(), game.getClearRow());
                if (gameOverPending) gameOverHandled = true; // Finished last tick
            }
        };
    }

    private void checkSpeedChange() {
        int desired = game.getFallSpeed();
        if (desired != currentSpeed) {
            boardTimeLine.stop();
            setupBoardTimeline();
            gameManager.updateGame();
        }
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            gameOverPending = true;
        }
        if (gameOverPending && gameOverHandled) {
            resultSetter.update(game.getGameResult());
            onGameOver.run(); // notify manager
        }
    }

    // Getter
    public Timeline getBoardTimeLine() {return boardTimeLine;}
    public AnimationTimer getGameLoop() {return gameLoop;}
}