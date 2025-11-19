package com.comp2042.colors;

import javafx.scene.paint.Paint;

public interface ThemeColor {

    // Board color
    Paint getPreviewColor();
    Paint getBoardColor();
    Paint getGridColor();
    Paint getBrickColor(int value);
    Paint getBrickOutline(int value);
    Paint getGhostColor(int value);

    // Vfx color
    Paint getOverlayColor();
    Paint getTextColor();
    Paint getClearRowColor();
    Paint getLevelUpColor();

}
