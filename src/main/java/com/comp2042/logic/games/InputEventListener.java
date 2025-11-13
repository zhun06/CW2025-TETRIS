package com.comp2042.logic.games;

import com.comp2042.logic.moves.MoveEvent;

public interface InputEventListener {

    void onDownEvent(MoveEvent event);

    void onLeftEvent(MoveEvent event);

    void onRightEvent(MoveEvent event);

    void onRotateEvent(MoveEvent event);

    void createNewGame();
}
