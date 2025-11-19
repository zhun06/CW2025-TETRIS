package com.comp2042.sfx;

import com.comp2042.logic.data.ClearRow;
import com.comp2042.util.SfxEvent;

public class SfxPlayer {

    public void play(SfxEvent event, ClearRow clearRow) {
        switch(event) {
            case GAME_START -> this.onStart();
            case HARD_DROP -> this.onHardDrop();
            case LINE_CLEAR -> this.onLineClear(clearRow);
            case LEVEL_UP ->  this.onLevelUp();
            case GAME_OVER ->  this.onGameOver();
        }
    }

    private void onStart() {
        SoundLoader.playMusic();
        SoundLoader.play(SfxEvent.GAME_START);
    }

    private void onHardDrop() {
        SoundLoader.play(SfxEvent.HARD_DROP);
    }

    private void onLineClear(ClearRow clearRow) {
        SoundLoader.play(SfxEvent.LINE_CLEAR);

        if (clearRow.getLinesRemoved() == 4) SoundLoader.play(SfxEvent.TETRIS);
    }

    private void onLevelUp() {
        SoundLoader.play(SfxEvent.LEVEL_UP);
    }

    private void onGameOver() {
        SoundLoader.stopMusic();
        SoundLoader.play(SfxEvent.GAME_OVER);
    }
}
