package com.comp2042.colors;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class NeonColor implements ThemeColor {

    @Override
    public Paint getPreviewColor() {return Color.web("#151528");}

    @Override
    public Paint getBoardColor() {return Color.web("#0a0f1f");}

    @Override
    public Paint getGridColor() {return Color.web("#22263D");}

    @Override
    public Paint getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#00F6FF");
            case 2 -> Color.web("#2F5BFF");
            case 3 -> Color.web("#C300FF");
            case 4 -> Color.web("#00FF85");
            case 5 -> Color.web("#FFE84D");
            case 6 -> Color.web("#FF9933");
            case 7 -> Color.web("#FF2B4F");
            default -> Color.TRANSPARENT;
        };
    }

    @Override
    public Paint getBrickOutline(int value) {return Color.web("#000000AA");}

    @Override
    public Paint getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#A6FAFF");
            case 2 -> Color.web("#AEBBFF");
            case 3 -> Color.web("#E8A6FF");
            case 4 -> Color.web("#A6FFCE");
            case 5 -> Color.web("#FFF3AE");
            case 6 -> Color.web("FFD1A6");
            case 7 -> Color.web("#FFA6B5");
            default -> Color.TRANSPARENT;
        };
    }

    // VFX colors
    @Override
    public Paint getOverlayColor() {return Color.rgb(20, 0, 40, 0.35);}
    @Override
    public Paint getTextColor() { return Color.web("#FF2B5C"); }             // neon pink overlay text
    @Override
    public Paint getClearRowColor() { return Color.web("#FFC800"); }         // yellow glow for cleared rows
    @Override
    public Paint getLevelUpColor() { return Color.web("#00FFFF"); }          // cyan ring for level up



}
