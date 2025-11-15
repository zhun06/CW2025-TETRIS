package com.comp2042.logic.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    private final IntegerProperty highScore = new SimpleIntegerProperty(0);

    private final IntegerProperty rowsCleared = new SimpleIntegerProperty(0);

    private final IntegerProperty level = new SimpleIntegerProperty(1);

    public void reset() {
        score.setValue(0);
        rowsCleared.setValue(0);
    }

    // Setters
    public void addScore(int i){score.setValue(score.getValue() + i);}
    public void addRowsCleared(int i) {rowsCleared.setValue(rowsCleared.getValue() + i);}
    public void setHighScore() {if (score.getValue() > highScore.getValue()) highScore.setValue(score.getValue());}
    public void setLevel(int i) {level.setValue(i);}


    // Getters
    public IntegerProperty scoreProperty() {return score;}
    public IntegerProperty highScoreProperty() {return highScore;}
    public IntegerProperty rowsClearedProperty() {return rowsCleared;}
    public IntegerProperty levelProperty() {return level;}
}
