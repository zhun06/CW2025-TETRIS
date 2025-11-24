package com.comp2042.colors;

import javafx.scene.paint.Color;

public class OceanColor implements ThemeColor {

    @Override
    public Color getPreviewColor() { return Color.web("#CBEFFF"); } // lighter preview
    @Override
    public Color getBoardColor() { return Color.web("#49B6E8"); }   // calm blue
    @Override
    public Color getGridColor() { return Color.web("#006A9A"); }    // darker visible grid

    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#005B96"); // strong ocean navy
            case 2 -> Color.web("#0099CC"); // bright cyan
            case 3 -> Color.web("#007EA7"); // aqua blue
            case 4 -> Color.web("#33CCFF"); // light cyan
            case 5 -> Color.web("#66D9FF"); // pale sky aqua
            case 6 -> Color.web("#99E6FF"); // icy light blue
            case 7 -> Color.web("#003F63"); // deep sea dark
            case 8 -> Color.web("#004F7A"); // deep reef blue
            default -> Color.TRANSPARENT;
        };
    }

    @Override
    public Color getBrickOutline(int value) {
        return Color.BLACK;
    }


    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#A9D7FF", 0.35); // light icy blue
            case 2 -> Color.web("#B5EBFF", 0.35);
            case 3 -> Color.web("#A0E4F5", 0.35);
            case 4 -> Color.web("#C7F5FF", 0.35);
            case 5 -> Color.web("#D2F9FF", 0.35);
            case 6 -> Color.web("#E2FCFF", 0.35);
            case 7 -> Color.web("#9FC7E8", 0.35);
            default -> Color.TRANSPARENT;
        };
    }

    // VFX colors
    @Override
    public Color getOverlayColor() { return Color.rgb(0, 130, 180, 0.25); }
    @Override
    public Color getTextColor() { return Color.web("#004B75"); }
    @Override
    public Color getClearRowColor() { return Color.web("#00AEEE"); }
    @Override
    public Color getLevelUpColor() { return Color.web("#33CCFF"); }
}
