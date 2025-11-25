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

/**
 * The core engine of the Tetris game. Responsible for setting up the game
 * timeline, rendering the board and previews, managing stats, handling
 * sound effects, and detecting game-over conditions.
 *
 * <p>This class orchestrates the main game loop via {@link AnimationTimer} and
 * a {@link Timeline} for falling pieces. It delegates rendering and UI updates
 * to helper classes like {@link BoardRenderer}, {@link PreviewRenderer},
 * {@link StatsSetter}, {@link ResultSetter}, and {@link SfxManager}.</p>
 */
public class TetrisEngine  {
    // GameManager (Boss)
    GameManager gameManager;
    // Helpers (created once and REUSED)
    private BoardRenderer boardRenderer;
    private PreviewRenderer previewRenderer;
    private StatsSetter statsSetter;
    private ResultSetter resultSetter;
    private SfxManager sfxManager;
    // Timeline for falling brick
    private Timeline boardTimeLine;
    // Main loop for rendering and updates
    private AnimationTimer gameLoop;
    // Callback when game is over
    private Runnable onGameOver;
    // Game over lags
    private boolean gameOverPending;
    private boolean gameOverHandled;

    private final TetrisGame game;
    private final GameController gameController;
    // Current speed of falling brick
    private int currentSpeed;

    /**
     * Constructs a new {@code TetrisEngine} with the provided game manager,
     * game model, and controller.
     *
     * @param gameManager the manager controlling game state
     * @param game the {@link TetrisGame} model
     * @param gameController the {@link GameController} for UI
     */
    public TetrisEngine(GameManager gameManager, TetrisGame game, GameController gameController) {
        this.gameManager = gameManager;
        this.game = game;
        this.gameController = gameController;
        this.initialize();
    }

    /**
     * Initializes helper objects and sets up the game-over callback.
     * Called once during construction.
     */
    private void initialize() {
        boardRenderer = new BoardRenderer(gameController, game.getBoard());
        previewRenderer = new PreviewRenderer(gameController);
        statsSetter = new StatsSetter(gameController);
        resultSetter = new ResultSetter(gameController);
        sfxManager = new SfxManager(gameController);
        onGameOver = gameManager.setOnGameOver();
    }

    /**
     * Starts a new game. Initializes a new game state, sets up the board
     * timeline and main game loop, and resets game-over flags.
     */
    public void start() {
        game.createNewGame();
        this.onNewGame();
        this.setupBoardTimeline();
        this.setupGameLoop();
        gameOverPending = false;
        gameOverHandled = false;
    }

    /**Called when starting a new game to notify all helpers to reset their state.*/
    private void onNewGame() {
        boardRenderer.onNewGame();
        previewRenderer.onNewGame();
        statsSetter.onNewGame();
        sfxManager.onNewGame();
    }

    /**
     * Sets up the {@link Timeline} controlling piece falling speed.
     * Updates the board and checks for speed changes.
     */
    private void setupBoardTimeline() {
        currentSpeed = game.getFallSpeed();
        boardTimeLine = new Timeline(new KeyFrame(Duration.millis(currentSpeed), e -> {
            game.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
            this.checkSpeedChange();
        }));
        boardTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Sets up the main {@link AnimationTimer} game loop. Responsible for
     * rendering the board, previews, updating stats, playing sound effects,
     * and checking for game-over.
     */
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

    /**
     * Checks whether the falling speed has changed. If so, resets the timeline
     * to use the new speed.
     */
    private void checkSpeedChange() {
        int desired = game.getFallSpeed();
        if (desired != currentSpeed) {
            boardTimeLine.stop();
            setupBoardTimeline();
            gameManager.updateGame();
        }
    }

    /**
     * Checks for game-over conditions. If the game is over, sets flags and
     * notifies the {@link GameManager} via the {@code onGameOver} callback.
     */
    private void checkGameOver() {
        if (game.isGameOver()) {
            gameOverPending = true;
        }
        if (gameOverPending && gameOverHandled) {
            resultSetter.update(game.getGameResult());
            onGameOver.run(); // notify manager
        }
    }

    /**
     * Returns the {@link Timeline} controlling falling pieces.
     * @return the board timeline
     */
    public Timeline getBoardTimeLine() {return boardTimeLine;}
    /**
     * Returns the main {@link AnimationTimer} game loop.
     * @return the animation timer
     */
    public AnimationTimer getGameLoop() {return gameLoop;}
}