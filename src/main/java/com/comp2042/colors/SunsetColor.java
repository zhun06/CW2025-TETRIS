package com.comp2042.colors;

import javafx.scene.paint.Color;

/**SunsetColor theme implementing {@link ThemeColor} interface.*/
public class SunsetColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() { return Color.web("#FFDDD2"); } // soft sunset pink

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() { return Color.web("#FFAB76"); } // warm orange board

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() { return Color.web("#FF8A5B"); } // deeper orange grid

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FF6F61"); // coral red
            case 2 -> Color.web("#FFB347"); // golden orange
            case 3 -> Color.web("#F48FB1"); // soft magenta
            case 4 -> Color.web("#FF5722"); // bright tangerine
            case 5 -> Color.web("#D81B60"); // deep pink
            case 6 -> Color.web("#FFD54F"); // pale yellow
            case 7 -> Color.web("#FF7043"); // warm peach
            case 8 -> Color.web("#CE93D8"); // lavender-pink
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) { return Color.web("#4E342E"); } // dark brown outline

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#E65100");
            case 2 -> Color.web("#FFA726");
            case 3 -> Color.web("#C2185B");
            case 4 -> Color.web("#E64A19");
            case 5 -> Color.web("#AD1457");
            case 6 -> Color.web("#FFB300");
            case 7 -> Color.web("#F57C00");
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() { return Color.rgb(255, 180, 130, 0.25); }
    /** {@inheritDoc} */
    @Override
    public Color getTextColor() { return Color.web("#D84315"); }       // deep orange text
    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() { return Color.web("#FFC107"); }   // golden glow
    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() { return Color.web("#FF7043"); }    // warm orange ring
}

