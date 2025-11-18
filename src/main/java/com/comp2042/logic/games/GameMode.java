package com.comp2042.logic.games;

public interface GameMode {
    default void onTick() {}            // Every gravity tick
    default void onLineClear() {}       // When lines cleared
    default void onGameStart() {}       // On new game
    default void onBoardFull() {}        // Board full
}


