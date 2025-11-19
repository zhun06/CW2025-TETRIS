package com.comp2042.sfx;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.SfxData;
import com.comp2042.util.SfxEvent;
import javafx.scene.canvas.Canvas;

import java.util.Deque;


public class SfxManager {
    private final Canvas vfxCanvas;
    private final VfxRenderer vfxRenderer;
    private final SfxPlayer sfxPlayer;

    // Constructor
    public SfxManager(GameController gameController) {
        this.vfxCanvas = gameController.getVfxCanvas();
        this.vfxRenderer = new VfxRenderer(vfxCanvas);
        this.sfxPlayer = new SfxPlayer();
    }

    public void update(SfxData data, ClearRow clearRow) {
        Deque<SfxEvent> activeSfxEvents = data.getActiveSfxEvents();
        SfxEvent event;

        while ((event = activeSfxEvents.poll()) != null) {
            vfxRenderer.render(event, clearRow);
            sfxPlayer.play(event, clearRow);
        }
    }

}
