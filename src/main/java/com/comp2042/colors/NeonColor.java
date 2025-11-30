package com.comp2042.colors;

import javafx.scene.paint.Color;

/**NeonColor theme implementing {@link ThemeColor} interface.*/
public class NeonColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() {
        return Color.web("#151528"); // deep indigo/purple
    }

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() {
        return Color.web("#0a0f1f"); // almost black navy
    }

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() {return Color.web("#22263D");} // dark slate blue

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#00F6FF"); // bright cyan neon
            case 2 -> Color.web("#2F5BFF"); // electric blue
            case 3 -> Color.web("#C300FF"); // vivid magenta neon
            case 4 -> Color.web("#00FF85"); // neon green-cyan
            case 5 -> Color.web("#FFE84D"); // bright yellow neon
            case 6 -> Color.web("#FF9933"); // glowing orange
            case 7 -> Color.web("#FF2B4F"); // hot pink/red neon
            case 8 -> Color.web("#FF00AA"); // neon magenta
            default -> Color.TRANSPARENT;   // fallback for invalid brick ID
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) {return Color.web("#000000AA");} // semi-transparent black outline for contrast

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#A6FAFF"); // pale cyan neon
            case 2 -> Color.web("#AEBBFF"); // soft blue neon
            case 3 -> Color.web("#E8A6FF"); // soft magenta neon
            case 4 -> Color.web("#A6FFCE"); // minty neon green
            case 5 -> Color.web("#FFF3AE"); // pale yellow
            case 6 -> Color.web("#FFD1A6"); // soft orange
            case 7 -> Color.web("#FFA6B5"); // pinkish neon
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() {return Color.rgb(20, 0, 40, 0.35);} // semi-transparent dark purple

    /** {@inheritDoc} */
    @Override
    public Color getTextColor() {return Color.web("#FF2B5C");} // neon pink


    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() {return Color.web("#00FFFF");} // bright cyan glow

    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() {return Color.web("#00FFFF");} // bright cyan ring

}
