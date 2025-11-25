package com.comp2042.sfx;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.SfxData;
import com.comp2042.util.SfxEvent;
import javafx.scene.canvas.Canvas;

import java.util.Deque;

/**
 * Manages both sound effects (SFX) and visual effects (VFX) for the Tetris game.
 * Delegates rendering of visual effects to {@link VfxRenderer} and playback of sound
 * to {@link SfxPlayer}. Processes active events from {@link SfxData}.
 */
public class SfxManager {
    private final VfxRenderer vfxRenderer;
    private final SfxPlayer sfxPlayer;

    /**
     * Constructs an SfxManager with the provided GameController.
     * @param gameController The controller for the current game scene.
     */
    public SfxManager(GameController gameController) {
        this.vfxRenderer = new VfxRenderer(gameController);
        this.sfxPlayer = new SfxPlayer();
    }

    /**Resets VFX state for a new game.*/
    public void onNewGame() {this.vfxRenderer.onNewGame();}

    /**
     * Processes active SFX events and updates visuals and audio accordingly.
     * @param data Active SFX events queue.
     * @param clearRow Data for cleared rows (used for line clear effects).
     */
    public void update(SfxData data, ClearRow clearRow) {
        Deque<SfxEvent> activeSfxEvents = data.getActiveSfxEvents();
        SfxEvent event;

        while ((event = activeSfxEvents.poll()) != null) {
            vfxRenderer.render(event, clearRow);
            sfxPlayer.play(event, clearRow);
        }
    }

}
