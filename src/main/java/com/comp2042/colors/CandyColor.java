package com.comp2042.colors;

import javafx.scene.paint.Color;

/**CandyColor theme implementing {@link ThemeColor} interface.*/
public class CandyColor implements ThemeColor {
    /** {@inheritDoc} */
    @Override
    public Color getPreviewColor() { return Color.web("#2E1F2F"); } // dark plum/pastel background

    /** {@inheritDoc} */
    @Override
    public Color getBoardColor() { return Color.web("#F890BBFF"); } // light pink board

    /** {@inheritDoc} */
    @Override
    public Color getGridColor() { return Color.web("#FAA9C8FF"); } // lighter pink grid for subtle contrast

    /** {@inheritDoc} */
    @Override
    public Color getBrickColor(int value) {
        return switch (value) {
            case 1 -> Color.web("#FF5A8D"); // bright bubblegum
            case 2 -> Color.web("#FFD93D"); // candy yellow
            case 3 -> Color.web("#7EC8FF"); // sky popsicle blue
            case 4 -> Color.web("#FF8A33"); // tangerine
            case 5 -> Color.web("#D06CFF"); // sweet lavender
            case 6 -> Color.web("#63E8C8"); // mint candy
            case 7 -> Color.web("#FF9A6E"); // peach candy
            case 8 -> Color.web("#E8B3FF"); // pastel sugar purple garbage
            default -> Color.TRANSPARENT;
        };
    }

    /** {@inheritDoc} */
    @Override
    public Color getBrickOutline(int value) { return Color.BLACK; } // darker outline for contrast

    /** {@inheritDoc} */
    @Override
    public Color getGhostColor(int value) {
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

    /** {@inheritDoc} */
    @Override
    public Color getOverlayColor() {return Color.rgb(255, 200, 220, 0.3);}
    /** {@inheritDoc} */
    @Override
    public Color getTextColor() { return Color.web("#F10D0DFF"); }             // candy pink overlay text
    /** {@inheritDoc} */
    @Override
    public Color getClearRowColor() { return Color.web("#FFD700"); }         // candy yellow glow
    /** {@inheritDoc} */
    @Override
    public Color getLevelUpColor() { return Color.web("#FF8C42"); }          // candy orange ring
}