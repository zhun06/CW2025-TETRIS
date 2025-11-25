package com.comp2042.util;

/**Represents types of actions that can occur in the game.*/
public enum EventType {
    /** Move the current piece down by one row. */
    DOWN,
    /** Instantly drop the piece to the bottom. */
    HARD_DROP,
    /** Move the piece one cell to the left. */
    LEFT,
    /** Move the piece one cell to the right. */
    RIGHT,
    /** Rotate the piece. */
    ROTATE
}
