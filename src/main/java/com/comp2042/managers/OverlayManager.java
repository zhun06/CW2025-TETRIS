package com.comp2042.managers;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

// Manages overlays & pop-ups
public class OverlayManager {
    private final Parent root;
    private final Pane pauseGamePane;
    private final Pane gameOverPane;

    public OverlayManager() {
        this.root = ControllerManager.getGameRoot();
        this.pauseGamePane = (Pane) root.lookup("#pauseGamePane"); // Find by fx:id
        this.gameOverPane = (Pane) root.lookup("#gameOverPane"); // Find by fx:id
    }

    public void showPauseGame() {
        pauseGamePane.setVisible(true);
    }

    public void hidePauseGame() {
        pauseGamePane.setVisible(false);
    }

    public void hideGameOver() {
        gameOverPane.setVisible(false);
    }

    public void showGameOver() {
        gameOverPane.setVisible(true);
    }

}