package com.comp2042.logic.games;

/**
 * Defines callbacks that represent key events during gameplay.
 * Modes can override these callbacks to implement specialized behavior.
 */
public interface GameMode {

    /**Called on every gravity tick while the game is running.*/
    default void onTick() {}

    /**Called when one or more rows are cleared.*/
    default void onLineClear() {}

    /**Called at the start of a new game.*/
    default void onGameStart() {}

    /**Called when the board becomes full or when a mode-specific end condition occurs.*/
    default void onBoardFull() {}
}


