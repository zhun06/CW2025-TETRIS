package com.comp2042.colors;

import javafx.scene.paint.Color;

public interface ThemeColor {

    // Board color
    Color getPreviewColor();
    Color getBoardColor();
    Color getGridColor();
    Color getBrickColor(int value);
    Color getBrickOutline(int value);
    Color getGhostColor(int value);

    // Vfx color
    Color getOverlayColor();
    Color getTextColor();
    Color getClearRowColor();
    Color getLevelUpColor();
}
