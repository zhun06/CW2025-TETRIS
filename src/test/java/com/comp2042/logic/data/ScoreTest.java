package com.comp2042.logic.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    @Test
    void onDownEvent_incrementsScoreBy1() {
        Score score = new Score();
        int initial = score.scoreProperty().get();
        score.onDownEvent();
        assertEquals(initial + 1, score.scoreProperty().get());
    }

    @Test
    void onHardDropEvent_incrementsScoreBy10() {
        Score score = new Score();
        int initial = score.scoreProperty().get();
        score.onHardDropEvent();
        assertEquals(initial + 10, score.scoreProperty().get());
    }

    @Test
    void addScore_calculatesBonusCorrectly() {
        Score score = new Score();
        // Bonus = 50 * lines^2
        score.addScore(1);
        assertEquals(50, score.scoreProperty().get());
        score.addScore(2);
        assertEquals(50 + 50*4, score.scoreProperty().get()); // 50 + 200 = 250
        score.addScore(3);
        assertEquals(250 + 50*9, score.scoreProperty().get()); // 250 + 450 = 700
    }

    @Test
    void onLineClear_updatesScoreAndRowsCleared() {
        Score score = new Score();
        ClearRow cr = new ClearRow(2, List.of(0,1), new int[0][0]);
        score.onLineClear(cr);

        // Score should be 50 * 2^2 = 200
        assertEquals(200, score.scoreProperty().get());
        // Rows cleared should be incremented by 2
        assertEquals(2, score.rowsClearedProperty().get());
    }

    @Test
    void addRowsCleared_updatesRowsClearedProperty() {
        Score score = new Score();
        score.addRowsCleared(3);
        assertEquals(3, score.rowsClearedProperty().get());
        score.addRowsCleared(2);
        assertEquals(5, score.rowsClearedProperty().get());
    }

}