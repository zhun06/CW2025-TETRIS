package com.comp2042.managers;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.util.GameState;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


// Manages overlays & pop-ups
public class OverlayManager {
    private final Pane pauseGamePane;
    private final Pane resultPane;
    private final Label resultLabel;

    public OverlayManager(GameController gameController) {

        this.pauseGamePane = gameController.getPauseGamePane();
        this.resultPane = gameController.getResultPane();
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


    public void showPauseGame() {pauseGamePane.setVisible(true);}

    public void hidePauseGame() {pauseGamePane.setVisible(false);}

    public void hideResult() {resultPane.setVisible(false);}

    public void showResult() {
        GameState result = GameManager.getResultState();
        switch (result) {
            case LOSE ->  {
                resultLabel.setText("You Lost");
                resultLabel.setBackground(Background.fill(Color.RED));
            }
            case WIN ->  {
                resultLabel.setText("You Won");
                resultLabel.setBackground(Background.fill(Color.GREEN));
            }
            case GAME_OVER -> resultLabel.setText("Game Over");
        }
        resultPane.setVisible(true);
    }

}