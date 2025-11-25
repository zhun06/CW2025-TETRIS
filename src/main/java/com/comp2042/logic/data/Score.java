package com.comp2042.logic.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Tracks all scoring-related data for a single game session.
 * Handles score increments, line clears, levels, and row statistics.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty rowsCleared = new SimpleIntegerProperty(0);
    private final IntegerProperty rowsRemaining = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(0);

    /** Adds score for a soft downward move. */
    public void onDownEvent() {score.setValue(score.getValue() + 1);}

    /** Adds score for a hard drop action. */
    public void onHardDropEvent() {score.setValue(score.getValue() + 10);}

    /**
     * Adds score and clears rows after a line-clear event.
     *
     * @param clearRow details about cleared rows
     */
    public void onLineClear(ClearRow clearRow) {
        this.addScore(clearRow.getLinesRemoved());
        this.addRowsCleared(clearRow.getLinesRemoved());
    }

    /**
     * Adds bonus score based on number of lines removed at once.
     *
     * @param linesRemoved number of lines removed
     */
    public void addScore(int linesRemoved) {
        int bonus = 50 * linesRemoved * linesRemoved;
        score.setValue(score.getValue() + bonus);
    }

    /** @param i number of rows to add to total cleared count */
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
