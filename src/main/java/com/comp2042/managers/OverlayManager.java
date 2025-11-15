package com.comp2042.managers;

import com.comp2042.controllers.GameController;
import com.comp2042.util.GameState;
import javafx.scene.layout.Pane;

// Manages overlays & pop-ups
public class OverlayManager {
    private final Pane pauseGamePane;
    private final Pane gameOverPane;

    public OverlayManager(GameController gameController) {
        this.pauseGamePane = gameController.getPauseGamePane();
        this.gameOverPane = gameController.getGameOverPane();
    }

    public void update() {
        GameState state = GameManager.getCurrentGameState();

        switch (state) {
            case PAUSE -> this.showPauseGame();
            case RESUME -> this.hidePauseGame();
            case RESTART, EXIT -> {
                this.hidePauseGame();
                this.hideGameOver();
            }
            case GAMEOVER -> this.showGameOver();
            case CHANGETHEME -> this.hideGameOver();
        }
    }


    public void showPauseGame() {pauseGamePane.setVisible(true);}

    public void hidePauseGame() {pauseGamePane.setVisible(false);}

    public void hideGameOver() {gameOverPane.setVisible(false);}

    public void showGameOver() {gameOverPane.setVisible(true);}

}