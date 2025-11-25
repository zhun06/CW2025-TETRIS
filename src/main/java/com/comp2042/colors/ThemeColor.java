package com.comp2042.colors;

import javafx.scene.paint.Color;

/**
 * Defines a color theme for the Tetris game. Implementations of this interface
 * return all colors used for the board, bricks, grid, VFX, and UI text.
 *
 * <p>Each {@code ThemeColor} supplies a consistent visual style that determines
 * how the game should be rendered. Implementations are responsible for providing
 * colors that match their intended theme (e.g., Ocean, Neon, Candy, etc.).</p>
 */
public interface ThemeColor {
    /**
     * Returns the color of the preview window where the next piece is shown.
     *
     * @return a {@link Color} used for the preview background
     */
    Color getPreviewColor();

    /**
     * Returns the background color of the Tetris board.
     *
     * @return a {@link Color} used for the board background
     */
    Color getBoardColor();

    /**
     * Returns the color of the grid lines drawn on the board.
     *
     * @return a {@link Color} used for grid lines
     */
    Color getGridColor();

    /**
     * Returns the fill color of a Tetris brick based on its value.
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} for the brick, or {@code TRANSPARENT} if invalid
     */
    Color getBrickColor(int value);

    /**
     * Returns the outline color for a brick based on its value.
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} for the brick outline
     */
    Color getBrickOutline(int value);

    /**
     * Returns the ghost-piece color for a given brick value.
     *
     * @param value the brick ID (1–8)
     * @return a {@link Color} representing the ghost piece
     */
    Color getGhostColor(int value);

    /**
     * Returns the overlay color used during effects such as pausing, clearing,
     * or transitions.
     *
     * @return a semi-transparent {@link Color} used for overlays
     */
    Color getOverlayColor();

    /**
     * Returns the theme's text color, used for UI labels such as score,
     * level, and messages.
     *
     * @return a {@link Color} used for text rendering
     */
    Color getTextColor();

    /**
     * Returns the highlight color used when a row is cleared.
     *
     * @return a {@link Color} used for row-clear VFX
     */
    Color getClearRowColor();

    /**
     * Returns the color used to indicate a level-up event.
     *
     * @return a {@link Color} for level-up animations or effects
     */
    Color getLevelUpColor();
}