package com.comp2042.managers;

import com.comp2042.controllers.GameController;
import com.comp2042.util.GameState;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


// Manages overlays & pop-ups
public class OverlayManager {
    private final Pane pauseGamePanel;
    private final Pane resultPanel;
    private final Label resultLabel;

    public OverlayManager(GameController gameController) {

        this.pauseGamePanel = gameController.getPauseGamePanel();
        this.resultPanel = gameController.getResultPanel();
        this.resultLabel = gameController.getResultLabel();
    }

    public void update() {
        GameState state = GameManager.getCurrentGameState();

        switch (state) {
            case PAUSE -> this.showPauseGame();
            case RESUME -> this.hidePauseGame();
            case RESTART, EXIT -> {
                this.hidePauseGame();
                this.hideResult();
            }
            case GAME_OVER -> this.showResult();
            case CHANGE_THEME -> this.hideResult();
        }
    }


    public void showPauseGame() {
        pauseGamePanel.setVisible(true);}

    public void hidePauseGame() {
        pauseGamePanel.setVisible(false);}

    public void hideResult() {
        resultPanel.setVisible(false);}

    public void showResult() {
        GameState result = GameManager.getResultState();
        switch (result) {
            case LOSE ->  {
                resultLabel.setText("You Lost");
                resultLabel.setTextFill(Color.RED);
            }
            case WIN ->  {
                resultLabel.setText("You Won");
                resultLabel.setTextFill(Color.GREEN);
            }
            case GAME_OVER -> {
                resultLabel.setText("Game Over");
                resultLabel.setTextFill(Color.DARKGRAY);
            }
        }
        resultPanel.setVisible(true);
    }

}