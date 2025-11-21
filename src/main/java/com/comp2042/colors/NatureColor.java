package com.comp2042.colors;

import javafx.scene.paint.Color;


public class NatureColor implements ThemeColor {

    @Override
    public Color getPreviewColor() { return Color.web("#B2E3A0FF"); } // soft pale green, calm background

    @Override
    public Color getBoardColor() { return Color.web("#9CD38CFF"); } // muted green, natural board tone

    @Override
    public Color getGridColor() { return Color.web("#8DAA91"); } // darker green, subtle grid

    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#2E7D32"); // rich forest green
            case 2 -> Color.web("#558B2F"); // deep moss green
            case 3 -> Color.web("#EF6C00"); // warm earthy orange
            case 4 -> Color.web("#0288D1"); // deep sky blue
            case 5 -> Color.web("#6A1B9A"); // deep flower purple
            case 6 -> Color.web("#FBC02D"); // golden yellow (like sunflower)
            case 7 -> Color.web("#5D4037"); // chocolate brown
            case 8 -> Color.web("#4B5A47"); // earthy muted dark green-brown
            default -> Color.TRANSPARENT;
        };
    }

    @Override
    public Color getBrickOutline(int value) {
        return Color.BLACK; //
    }

    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#81C784"); // faded forest green
            case 2 -> Color.web("#7CB342"); // muted moss green
            case 3 -> Color.web("#FFB74D"); // soft earthy orange
            case 4 -> Color.web("#4FC3F7"); // muted sky blue
            case 5 -> Color.web("#AB47BC"); // muted flower purple
            case 6 -> Color.web("#FFF176"); // soft sunflower yellow
            case 7 -> Color.web("#8D6E63"); // lighter brown
            default -> Color.TRANSPARENT;
        };
    }

    // VFX colors
    @Override
    public Color getOverlayColor() {return Color.rgb(30, 80, 30, 0.25);}
    @Override
    public Color getTextColor() { return Color.web("#2E7D32"); }             // forest green
    @Override
    public Color getClearRowColor() { return Color.web("#6FCD73FF"); }         // soft green glow for cleared rows
    @Override
    public Color getLevelUpColor() { return Color.web("#66BB6A"); }          // leafy green ring


}