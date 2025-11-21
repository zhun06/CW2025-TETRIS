package com.comp2042.logic.bricks;

import java.util.Queue;

public interface BrickGenerator {

    Brick getBrick();

    Queue<Brick> getNextBricks();
}
