package com.comp2042.controllers;

import com.comp2042.managers.SceneManager;
import com.comp2042.managers.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for the in-game UI. Manages the Tetris board, game state,
 * score and time labels, previews, pause and game-over panels, and user input.
 *
 * <p>Handles button actions for pausing, restarting, exiting, resuming,
 * and viewing leaderboards. Provides keyboard shortcuts for fullscreen toggle
 * and other in-game actions.</p>
 */
public class GameController {
    @FXML
    public StackPane root;

    @FXML // In game labels
    private Label levelLabel, timeLabel, rowsLabel, currentScoreLabel, highScoreLabel;

    @FXML // End game labels
    private Label gameResultLabel, timeResultLabel, rowsResultLabel, scoreResultLabel, highScoreResultLabel;

    @FXML
    private GridPane gameBoard;

    @FXML // VFX canvases
    private Canvas sweepCanvas, popupCanvas, levelUpCanvas, gameOverCanvas;

    @FXML // Brick preview
    private GridPane preview1, preview2, preview3;

    @FXML // Overlay panels
    private VBox pauseGamePanel, resultPanel;

    @FXML // In game buttons
    private Button pauseBtn, restartBtn1, exitBtn1;

    @FXML // Pause game buttons
    private Button resumeBtn, restartBtn2, exitBtn2;

    @FXML // End game buttons
    private Button playAgainBtn, leaderBoardBtn, exitBtn3;

    // Manage game states
    private GameManager gameManager;

    @FXML
    public void initialize() {
        // Block focus
        pauseBtn.setFocusTraversable(false);
        restartBtn1.setFocusTraversable(false);
        exitBtn1.setFocusTraversable(false);
    }

    /**
     * Sets the game manager instance to control game logic.
     *
     * @param gameManager the {@link GameManager} controlling the game
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Handles in-game button clicks (pause, restart, exit).
     *
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML // In game
    private void onButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == pauseBtn) {
            gameManager.pauseGame();
        }
        if (event.getSource() == restartBtn1) {
            gameManager.restartGame();
        }
        if (event.getSource() == exitBtn1) {
            gameManager.exitGame();
        }
    }

    /**
     * Handles pause menu button clicks (resume, restart, exit).
     *
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML // Pause game
    private void onPauseButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == resumeBtn) {
            gameManager.resumeGame();
        }
        if (event.getSource() == restartBtn2) {
            gameManager.restartGame();
        }
        if (event.getSource() == exitBtn2) {
            gameManager.exitGame();
        }
    }

    /**
     * Handles end-game button clicks (play again, leaderboard, exit).
     *
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML // Game Over
    private void onGameOverButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == playAgainBtn) {
            gameManager.restartGame();
        }
        if (event.getSource() == leaderBoardBtn) {
            gameManager.viewLeaderBoard();
        }
        if (event.getSource() == exitBtn3) {
            gameManager.exitGame();
        }
    }

    /**
     * Adds key event handler to the root pane, e.g., toggle fullscreen on 'F'.
     */
    public void addKeyHandler() {
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.F) stage.setFullScreen(!stage.isFullScreen());
        });
    }

    // Getters
    public List<Label> getGameLabels() {return new ArrayList<>(Arrays.asList(levelLabel, timeLabel, rowsLabel, currentScoreLabel, highScoreLabel));}
    public GridPane getGameBoard() {return gameBoard;}
    public List<Canvas> getVfxCanvases() {return new ArrayList<>(Arrays.asList(sweepCanvas, popupCanvas, levelUpCanvas, gameOverCanvas));}
    public List<GridPane> getPreviewGrids() {return new ArrayList<>(Arrays.asList(preview1, preview2, preview3));}
    public VBox getPauseGamePanel() {return pauseGamePanel;}
    public VBox getResultPanel() {return resultPanel;}
    public List<Label> getResultLabels() {return new ArrayList<>(Arrays.asList(gameResultLabel, timeResultLabel, rowsResultLabel, scoreResultLabel, highScoreResultLabel));}
}