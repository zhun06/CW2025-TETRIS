package com.comp2042.logic.moves;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.IBrick;
import com.comp2042.logic.bricks.JBrick;
import com.comp2042.logic.data.NextShapeInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {
    @Test
    void setBrick_resetsCurrentShape() {
        BrickRotator rot = new BrickRotator();
        Brick b = new IBrick();
        rot.setBrick(b);

        int[][] expected = b.getShapeMatrix().get(0);
        int[][] actual = rot.getCurrentShape();

        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }

    @Test // Cycles across all possible shape
    void getNextShape_IBrick() {
        BrickRotator rot = new BrickRotator();
        Brick b = new IBrick();
        rot.setBrick(b);
        int totalShapes = b.getShapeMatrix().size();
        for (int i = 0; i < totalShapes; i++) {
            NextShapeInfo info = rot.getNextShape();
            assertNotNull(info.getShape());
        }
    }

    @Test // Cycles across all possible shape
    void getNextShape_JBrick() {
        BrickRotator rot = new BrickRotator();
        Brick b = new JBrick();
        rot.setBrick(b);
        int totalShapes = b.getShapeMatrix().size();
        for (int i = 0; i < totalShapes; i++) {
            NextShapeInfo info = rot.getNextShape();
            assertNotNull(info.getShape());
        }
    }

}