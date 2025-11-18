package com.comp2042.managers;

import com.comp2042.controllers.GameController;
import com.comp2042.util.GameState;
import javafx.scene.layout.Pane;


// Manages overlays & pop-ups
public class OverlayManager {
    private final Pane pauseGamePanel;
    private final Pane resultPanel;


    public OverlayManager(GameController gameController) {
        this.pauseGamePanel = gameController.getPauseGamePanel();
        this.resultPanel = gameController.getResultPanel();
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
            case LEADER_BOARD -> this.hideResult();
        }
    }


    public void showPauseGame() {pauseGamePanel.setVisible(true);}

    public void hidePauseGame() {pauseGamePanel.setVisible(false);}

    public void hideResult() {resultPanel.setVisible(false);}

    public void showResult() {resultPanel.setVisible(true);}

}