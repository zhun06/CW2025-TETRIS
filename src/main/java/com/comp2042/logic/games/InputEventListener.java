package com.comp2042.logic.games;

import com.comp2042.logic.moves.MoveEvent;

/**
 * Listener interface for handling user and system-generated input events
 * that control brick movement and game flow.
 */
public interface InputEventListener {
    /**
     * Handles the event where the brick is moved downward.
     * @param event the event describing the movement source (user or gravity)
     */
    void onDownEvent(MoveEvent event);

    /**
     * Handles the event where the brick is moved left.
     * @param event the event describing the movement source
     */
    void onLeftEvent(MoveEvent event);

    /**
     * Handles the event where the brick is moved right.
     * @param event the event describing the movement source
     */
    void onRightEvent(MoveEvent event);

    /**
     * Handles the event where the brick is rotated.
     * @param event the event describing the movement source
     */
    void onRotateEvent(MoveEvent event);

    /**
     * Handles the event where the brick is hard-dropped.
     * @param event the event describing the movement source
     */
    void onHardDropEvent(MoveEvent event);

    /*** Creates and initializes a new game session.*/
    void createNewGame();
}