package com.comp2042.logic.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

// In game stats (new Score every game)
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty rowsCleared = new SimpleIntegerProperty(0);
    private final IntegerProperty rowsRemaining = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(0);

    public void onDownEvent() {score.setValue(score.getValue() + 1);}

    public void onHardDropEvent() {score.setValue(score.getValue() + 10);}

    public void onLineClear(ClearRow clearRow) {
        this.addScore(clearRow.getLinesRemoved());
        this.addRowsCleared(clearRow.getLinesRemoved());
    }

    public void addScore(int linesRemoved) {
        int bonus = 50 * linesRemoved * linesRemoved;
        score.setValue(score.getValue() + bonus);
    }

    public void addRowsCleared(int i) {rowsCleared.setValue(rowsCleared.getValue() + i);}

    // Setters
    public void setRowsRemaining(int i) {rowsRemaining.setValue(i);}
    public void setLevel(int i) {level.setValue(i);}

    // Getters
    public IntegerProperty scoreProperty() {return score;}
    public IntegerProperty rowsClearedProperty() {return rowsCleared;}
    public IntegerProperty rowsRemainingProperty() {return rowsRemaining;}
    public IntegerProperty levelProperty() {return level;}

}
