package com.comp2042.logic.games;

import com.comp2042.logic.data.DownData;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.moves.MoveEvent;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    void createNewGame();
}
