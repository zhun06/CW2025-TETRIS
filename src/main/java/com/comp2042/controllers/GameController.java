package com.comp2042.controllers;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;
import com.comp2042.managers.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameController {
    @FXML
    private Label timeLabel, rowsLabel, currentScoreLabel, highScoreLabel;

    @FXML
    private GridPane gameBoard;

    @FXML
    public Canvas vfxCanvas;

    @FXML
    private GridPane preview1, preview2, preview3;

    @FXML
    private VBox pauseGamePane, gameOverPane;

    @FXML // In Game
    private Button pauseBtn, restartBtn1, exitBtn1;

    @FXML // Pause Game
    private Button resumeBtn, restartBtn2, exitBtn2;

    @FXML // End Game
    private Button playAgainBtn, themeBtn, exitBtn3;



    @FXML
    public void initialize() {
        // Block focus
        pauseBtn.setFocusTraversable(false);
        restartBtn1.setFocusTraversable(false);
        exitBtn1.setFocusTraversable(false);
    }

    @FXML // In Game
    private void onButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == pauseBtn) {
            GameManager.pauseGame();
        }
        if (event.getSource() == restartBtn1) {
            GameManager.restartGame();
        }
        if (event.getSource() == exitBtn1) {
            GameManager.exitGame();
        }
    }

    @FXML // Pause Game
    private void onPauseButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == resumeBtn) {
            GameManager.resumeGame();
        }
        if (event.getSource() == restartBtn2) {
            GameManager.restartGame();
        }
        if (event.getSource() == exitBtn2) {
            GameManager.exitGame();
        }
    }

    @FXML // Game Over
    private void onGameOverButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == playAgainBtn) {
            GameManager.restartGame();
        }
        if (event.getSource() == themeBtn) {
            GameManager.changeTheme();
        }
        if (event.getSource() == exitBtn3) {
            GameManager.exitGame();
        }
    }

    public void addKeyHandler() {
        Parent root = ControllerManager.getGameRoot();
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.F) stage.setFullScreen(!stage.isFullScreen());
        });
    }

    // Getters
    public List<Label> getGameLabels() {return new ArrayList<>(Arrays.asList(timeLabel, rowsLabel, currentScoreLabel, highScoreLabel));}
    public GridPane getGameBoard() {return gameBoard;}
    public Canvas getVfxCanvas() {return vfxCanvas;}
    public List<GridPane> getPreviewPanels() {return new ArrayList<>(Arrays.asList(preview1, preview2, preview3));}
    public VBox getPauseGamePane() {return pauseGamePane;}
    public VBox getGameOverPane() {return gameOverPane;}
}