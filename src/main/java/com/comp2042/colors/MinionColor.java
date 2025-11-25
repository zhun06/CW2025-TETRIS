package com.comp2042.colors;

import javafx.scene.paint.Color;

/**MinionColor theme implementing {@link ThemeColor} interface.*/
public class MinionColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() { return Color.web("#FFF9C4"); } // soft banana yellow background

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() { return Color.web("#FFEB3B"); } // bright banana board

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() { return Color.web("#FDD835"); } // slightly darker yellow grid

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FBC02D"); // classic banana yellow
            case 2 -> Color.web("#1976D2"); // minion blue overalls
            case 3 -> Color.web("#FF6F00"); // orange highlights
            case 4 -> Color.web("#C2185B"); // playful pink accent
            case 5 -> Color.web("#388E3C"); // green accent
            case 6 -> Color.web("#7B1FA2"); // purple accent
            case 7 -> Color.web("#FFA000"); // golden yellow
            case 8 -> Color.web("#03A9F4"); // light blue
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) { return Color.BLACK; }

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FDD835"); // darker banana
            case 2 -> Color.web("#1565C0"); // darker blue
            case 3 -> Color.web("#E65100"); // darker orange
            case 4 -> Color.web("#AD1457"); // darker pink
            case 5 -> Color.web("#2E7D32"); // darker green
            case 6 -> Color.web("#6A1B9A"); // darker purple
            case 7 -> Color.web("#FF8F00"); // golden glow
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() { return Color.rgb(255, 255, 180, 0.25); } // translucent yellow overlay
    /** {@inheritDoc} */
    @Override
    public Color getTextColor() { return Color.web("#FBC02D"); } // bright yellow text
    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() { return Color.web("#FFEB3B"); } // bright banana glow
    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() { return Color.web("#FDD835"); } // golden ring for level up
}