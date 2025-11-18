package com.comp2042.engines;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.managers.GameManager;
import com.comp2042.renderers.*;
import com.comp2042.setters.ResultSetter;
import com.comp2042.setters.StatsSetter;
import com.comp2042.util.EventSource;
import com.comp2042.util.EventType;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

// Setup timeline and rendering
public class TetrisEngine  {
    private BoardRenderer boardRenderer;
    private PreviewRenderer previewRenderer;
    private StatsSetter statsSetter;
    private ResultSetter resultSetter;

    private Timeline boardTimeLine;
    private AnimationTimer gameLoop;
    private Runnable onGameOver;

    private final TetrisGame game;
    private final GameController gameController;

    private int currentSpeed;

    // Constructor
    public TetrisEngine(TetrisGame game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
    }

    public void start() {
        game.createNewGame();
        this.initialize();
        this.setupBoardTimeline();
        this.setupGameLoop();
    }

    private void initialize() {
        statsSetter = new StatsSetter(gameController);
        boardRenderer = new BoardRenderer(gameController, game.getBoard());
        previewRenderer = new PreviewRenderer(gameController);
        resultSetter = new ResultSetter(gameController);
        onGameOver = GameManager.setOnGameOver();
    }

    private void setupBoardTimeline() {
        currentSpeed = game.getFallSpeed();
        boardTimeLine = new Timeline(new KeyFrame(Duration.millis(currentSpeed), e -> {
            game.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
            this.checkSpeedChange();
            checkGameOver();
        }));
        boardTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                statsSetter.update(game.getScoreData(), game.getTimeData().getRemainingTime(), game.getTimeData().getElapsedTime(), game.getGameResult());
                boardRenderer.render(game.getViewData());
                previewRenderer.render(game.getNextBricks());
            }
        };
    }

    private void checkSpeedChange() {
        int desired = game.getFallSpeed();
        if (desired != currentSpeed) {
            boardTimeLine.stop();
            setupBoardTimeline();
            GameManager.updateGame();
        }
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            resultSetter.update(game.getGameResult());
            onGameOver.run(); // Notify GameManager
        }
    }

    // Getter
    public Timeline getBoardTimeLine() {return boardTimeLine;}
    public AnimationTimer getGameLoop() {return gameLoop;}
}