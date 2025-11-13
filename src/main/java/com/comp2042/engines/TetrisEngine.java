package com.comp2042.engines;

import com.comp2042.logic.games.TetrisGame;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.managers.GameManager;
import com.comp2042.renderers.Renderer;
import com.comp2042.util.EventSource;
import com.comp2042.util.EventType;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TetrisEngine  {
    private Renderer renderer; // Interface, render based on theme
    private Timeline boardTimeLine;
    private AnimationTimer gameLoop;
    private int boardTick = 400; // milliseconds per update
    private final int inputTick = 10;
    private Runnable onGameOver;

    private final TetrisGame game;

    private String currentTheme;

    // Constructor
    public TetrisEngine(TetrisGame game) {
        this.game = game;
    }

    public void start() {
        game.createNewGame();
        this.initialize();
        this.setupBoardTimeline();
        this.setupGameLoop();
    }

    private void initialize() {
//        currentTheme = SceneManager.getTheme();
        renderer = new Renderer(game.getBoard());
//        this.setRenderer();
        onGameOver = GameManager.setOnGameOver();
    }

    // Decides which ThemeRenderer to use for each theme
//    private void setRenderer() {
//        switch (currentTheme) {
//            case "retro" -> renderer = new RetroRenderer(game);
//            case "neon" -> renderer = new NeonRenderer(game);
//            case "candy" -> renderer = new CandyRenderer(game);
//        }
//    }

    private void setupBoardTimeline() {
        boardTimeLine = new Timeline(new KeyFrame(Duration.millis(boardTick), e -> {
            game.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
            this.updateSpeed();
            checkGameOver();
        }));
        boardTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderer.render(game.getViewData());
            }
        };
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            onGameOver.run(); // Notify GameManager
        }
    }

    private void updateSpeed() {
        double multiplier = 1.0;
        double capped = Math.min(multiplier, 2.0); // prevent insane speeds
        boardTimeLine.setRate(capped);
    }

    // Getter
    public Timeline getBoardTimeLine() {return boardTimeLine;}
    public AnimationTimer getGameLoop() {return gameLoop;}
}