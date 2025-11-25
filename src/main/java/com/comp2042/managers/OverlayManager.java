package com.comp2042.managers;

import com.comp2042.controllers.GameController;
import com.comp2042.util.GameState;
import javafx.scene.layout.Pane;


/**
 * Manages all in-game overlays such as pause menus and result panels.
 * <p>
 * Shows or hides panels depending on the active {@link GameState} and ensures
 * UI overlays correctly reflect gameplay events such as pausing, restarting,
 * or reaching game over.
 */
public class OverlayManager {
    private final Pane pauseGamePanel;
    private final Pane resultPanel;

    /** Constructs an OverlayManager instance to get overlay panels
     * {@param gameController}
     */
    public OverlayManager(GameController gameController) {
        this.pauseGamePanel = gameController.getPauseGamePanel();
        this.resultPanel = gameController.getResultPanel();
    }

    /** Updates overlay visibility based on current game state. */
    public void update() {
        GameState state = GameManager.getCurrentGameState();
        switch (state) {
            case PAUSE -> this.showPauseGame();
            case RESUME -> this.hidePauseGame();
            case RESTART, EXIT -> { this.hidePauseGame(); this.hideResult(); }
            case GAME_OVER -> this.showResult();
            case LEADER_BOARD -> this.hideResult();
        }
    }

    /** Shows pause panel. */
    public void showPauseGame() { pauseGamePanel.setVisible(true); }

    /** Hides pause panel. */
    public void hidePauseGame() { pauseGamePanel.setVisible(false); }

    /** Hides result panel. */
    public void hideResult() { resultPanel.setVisible(false); }

    /** Shows result panel. */
    public void showResult() { resultPanel.setVisible(true); }
}