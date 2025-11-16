package com.comp2042.colors;

import javafx.scene.paint.Paint;

public interface ThemeColor {

    Paint getPreviewColor();

    Paint getBoardColor();

    Paint getGridColor();

    Paint getBrickColor(int value);

    Paint getBrickOutline(int value);

    Paint getGhostColor(int value);

}
