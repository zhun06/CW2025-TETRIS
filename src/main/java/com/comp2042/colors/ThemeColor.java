package com.comp2042.colors;

import javafx.scene.paint.Color;

/**
 * Represents a color theme for the Tetris game. Implementations of this interface
 * provide all colors used for the game board, bricks, grid lines, visual effects,
 * and UI text.
 *
 * <p>Each {@code ThemeColor} defines a consistent visual style that controls how
 * the game is rendered. Implementations should supply colors that match their
 * intended theme (e.g., Ocean, Neon, Candy, etc.).</p>
 */
public interface ThemeColor {

    /**
     * Returns the color used for the preview window that shows the next piece.
     *
     * @return a {@link Color} used as the preview background
     */
    Color getPreviewColor();

    /**
     * Returns the background color of the Tetris board.
     *
     * @return a {@link Color} used as the board background
     */
    Color getBoardColor();

    /**
     * Returns the color of the grid lines displayed on the Tetris board.
     *
     * @return a {@link Color} used for grid lines
     */
    Color getGridColor();

    /**
     * Returns the fill color of a Tetris brick for the specified brick ID.
     *
     * <p>Valid brick IDs are from 1 to 8. If an invalid value is provided,
     * {@link Color#TRANSPARENT} is returned.</p>
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} used to fill the brick
     */
    Color getBrickColor(int value);

    /**
     * Returns the outline color of a Tetris brick for the specified brick ID.
     *
     * <p>Valid brick IDs are from 1 to 8.</p>
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} used for the brick outline
     */
    Color getBrickOutline(int value);

    /**
     * Returns the color of the ghost piece for the specified brick ID.
     *
     * <p>Ghost pieces are shown as a visual preview of where a brick will land.
     * Valid brick IDs are from 1 to 8. Invalid values return {@link Color#TRANSPARENT}.</p>
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} representing the ghost piece
     */
    Color getGhostColor(int value);

    /**
     * Returns the overlay color used for effects such as pausing, clearing rows,
     * or transitions.
     *
     * @return a semi-transparent {@link Color} used as an overlay
     */
    Color getOverlayColor();

    /**
     * Returns the color used for UI text such as score, level, and messages.
     *
     * @return a {@link Color} used for text rendering
     */
    Color getTextColor();

    /**
     * Returns the highlight color applied when a row is cleared.
     *
     * @return a {@link Color} used for row-clear visual effects
     */
    Color getClearRowColor();

    /**
     * Returns the color used to indicate a level-up event.
     *
     * @return a {@link Color} used for level-up animations or effects
     */
    Color getLevelUpColor();
}