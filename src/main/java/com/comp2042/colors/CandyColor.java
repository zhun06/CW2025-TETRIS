package com.comp2042.colors;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CandyColor implements ThemeColor {

    @Override
    public Paint getPreviewColor() { return Color.web("#2E1F2F"); } // dark plum/pastel background

    @Override
    public Paint getBoardColor() { return Color.web("#F890BBFF"); } // light pink board

    @Override
    public Paint getGridColor() { return Color.web("#FAA9C8FF"); } // lighter pink grid for subtle contrast

    @Override
    public Paint getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#E04C70"); // deeper bubblegum pink
            case 2 -> Color.web("#E6B800"); // darker candy yellow
            case 3 -> Color.web("#6BB3E1"); // deeper sky blue
            case 4 -> Color.web("#FF6F00"); // tangerine/orange candy
            case 5 -> Color.web("#B95ACD"); // richer lavender
            case 6 -> Color.web("#5ED1B5"); // darker mint green
            case 7 -> Color.web("#E0835B"); // deeper peachy-orange
            default -> Color.TRANSPARENT;
        };
    }

    @Override
    public Paint getBrickOutline(int value) { return Color.BLACK; } // darker outline for contrast

    @Override
    public Paint getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#B53A5C"); // darker bubblegum
            case 2 -> Color.web("#B38F00"); // darker candy yellow
            case 3 -> Color.web("#4B88B5"); // darker sky blue
            case 4 -> Color.web("#CC5500"); // darker tangerine
            case 5 -> Color.web("#9441A0"); // darker lavender
            case 6 -> Color.web("#429D89"); // darker mint
            case 7 -> Color.web("#B86446"); // darker peach
            default -> Color.TRANSPARENT;
        };
    }

    // VFX colors
    @Override
    public Paint getOverlayColor() {return Color.rgb(255, 200, 220, 0.3);}
    @Override
    public Paint getTextColor() { return Color.web("#F10D0DFF"); }             // candy pink overlay text
    @Override
    public Paint getClearRowColor() { return Color.web("#FFD700"); }         // candy yellow glow
    @Override
    public Paint getLevelUpColor() { return Color.web("#FF8C42"); }          // candy orange ring

}