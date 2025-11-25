package com.comp2042.colors;

import javafx.scene.paint.Color;

/**NeonColor theme implementing {@link ThemeColor} interface.*/
public class NeonColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() {return Color.web("#151528");}

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() {return Color.web("#0a0f1f");}

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() {return Color.web("#22263D");}

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#00F6FF");
            case 2 -> Color.web("#2F5BFF");
            case 3 -> Color.web("#C300FF");
            case 4 -> Color.web("#00FF85");
            case 5 -> Color.web("#FFE84D");
            case 6 -> Color.web("#FF9933");
            case 7 -> Color.web("#FF2B4F");
            case 8 -> Color.web("#FF00AA"); // neon magenta garbage
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) {return Color.web("#000000AA");}

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
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

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() {return Color.rgb(20, 0, 40, 0.35);}
    /** {@inheritDoc} */
    @Override
    public Color getTextColor() { return Color.web("#FF2B5C"); }             // neon pink overlay text
    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() { return Color.web("#00FFFF");}         // neon cyan
    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() { return Color.web("#00FFFF"); }          // cyan ring for level up
}
