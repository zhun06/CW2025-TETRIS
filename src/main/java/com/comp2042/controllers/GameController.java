package com.comp2042.controllers;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;
import com.comp2042.managers.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import java.io.IOException;


public class GameController {

    @FXML // In Game
    private Button pauseBtn, restartBtn1, exitBtn1;

    @FXML // Pause Game
    private Button resumeBtn, restartBtn2, exitBtn2;

    @FXML // End Game
    private Button playAgainBtn, themeBtn, exitBtn3;

    @FXML
    public GridPane brickPreview;
    public VBox previewPane;

    @FXML
    private GridPane gamePanel;


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
}