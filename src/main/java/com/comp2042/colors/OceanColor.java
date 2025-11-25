package com.comp2042.colors;

import javafx.scene.paint.Color;

/**OceanColor theme implementing {@link ThemeColor} interface.*/
public class OceanColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() { return Color.web("#FFE7D9"); } // soft warm coral wash

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() { return Color.web("#FFBFA5"); }   // pastel coral/orange

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() { return Color.web("#CC6E57"); }    // warm reef brownish-red

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FF6F61"); // warm coral red
            case 2 -> Color.web("#FF9671"); // peach coral
            case 3 -> Color.web("#FFC75F"); // reef yellow
            case 4 -> Color.web("#F9F871"); // pastel reef sand
            case 5 -> Color.web("#4DC6C1"); // turquoise reef water
            case 6 -> Color.web("#36A9A3"); // teal reef shadows
            case 7 -> Color.web("#845EC2"); // reef purple coral
            case 8 -> Color.web("#D65DB1"); // vibrant sea anemone pink
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) {return Color.web("#5A3F37");} // warm dark brown — fits coral theme better than black

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FFD4CC"); // light coral tint
            case 2 -> Color.web("#FFE1D2"); // pale peach
            case 3 -> Color.web("#FFF2CC"); // light reef yellow
            case 4 -> Color.web("#FFFFD9"); // pale sand glow
            case 5 -> Color.web("#DFF8F6"); // pale turquoise
            case 6 -> Color.web("#D4F2F1"); // soft teal mist
            case 7 -> Color.web("#E8DBFF"); // pastel reef purple
            case 8 -> Color.web("#F7D7EE"); // soft anemone pink
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() { return Color.rgb(255, 150, 120, 0.25); } // warm coral glow
    /** {@inheritDoc} */
    @Override
    public Color getTextColor() { return Color.web("#5A2E2E"); }            // warm deep brown
    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() { return Color.web("#FF9671"); }        // bright coral pop
    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() { return Color.web("#FFC75F"); }         // reef yellow flash
}
