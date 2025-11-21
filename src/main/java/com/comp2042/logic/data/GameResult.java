package com.comp2042.logic.data;

import com.comp2042.highScore.ScoreRecord;
import com.comp2042.highScore.CsvLoader;
import com.comp2042.managers.GameManager;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;

public final class GameResult {
    private GameState gameState;
    private final GameChoice mode;
    private final CsvLoader csvLoader;

    private final IntegerProperty endScore = new SimpleIntegerProperty(0);
    private final IntegerProperty highScore = new SimpleIntegerProperty(0);
    private final IntegerProperty endLevel = new SimpleIntegerProperty(0);
    private final IntegerProperty endRows = new SimpleIntegerProperty(0);
    private final IntegerProperty mostRows = new SimpleIntegerProperty(0);
    private final ObjectProperty<Duration> endTime = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Duration> bestTime = new SimpleObjectProperty<>(null);

    public GameResult() {
        mode = GameManager.getCurrentGameChoice();
        csvLoader = new CsvLoader();
        csvLoader.load();

        highScore.setValue(csvLoader.get(mode).score);
        mostRows.setValue(csvLoader.get(mode).rows);
        bestTime.setValue(csvLoader.get(mode).time);
    }

    public void update(Score score, Duration elapsedTime) {
        this.setEndScore(score);
        this.setEndRows(score);
        this.setEndTime(elapsedTime);
        this.setEndLevel(score);

        this.updateHighScore();
        this.updateMostRows();
        this.updateBestTime();

        // Update csv
        ScoreRecord scoreRecord = new ScoreRecord(mode, bestTime.getValue(), highScore.getValue(), mostRows.getValue());
        csvLoader.update(mode, scoreRecord);
    }


    // Setters
    public void setGameState(GameState gameState) {this.gameState = gameState;}
    public void setEndScore(Score score) {this.endScore.set(score.scoreProperty().get());}
    public void setEndRows(Score score) {this.endRows.set(score.rowsClearedProperty().get());}
    public void setEndTime(Duration endTime) {this.endTime.set(endTime);}
    public void setEndLevel(Score score) {this.endLevel.set(score.levelProperty().get());}

    // Update
    public void updateHighScore() { // Only for ZEN
        if (mode.equals(GameChoice.ZEN)) {
            if (endScore.getValue() > highScore.getValue()){
                highScore.setValue(endScore.getValue());
            }
        }

    }
    public void updateMostRows() { // Only for BLITZ
        if  (mode.equals(GameChoice.BLITZ)) {
            if (endRows.getValue() > mostRows.getValue()) {
                mostRows.setValue(endRows.getValue());
            }
        }
    }

    public void updateBestTime() { // For FORTY_LINES and HARD_CORE
        if (gameState == GameState.LOSE || endTime.get() == Duration.ZERO) return;
        if (bestTime.get() == null || bestTime.get().isZero()) {
            bestTime.setValue(endTime.getValue());
            return;
        }
        switch (mode) {
            case FORTY_LINES -> { // Least time
                if (endTime.get().compareTo(bestTime.get()) < 0){
                    bestTime.set(endTime.get());
                }
            }
            case HARDCORE ->  { // Longest time
                if (endTime.get().compareTo(bestTime.get()) > 0){
                    bestTime.set(endTime.get());
                }
            }
        }
    }

    // Getters
    public GameState getGameState() {return gameState;}
    public IntegerProperty endScoreProperty() {return endScore;}
    public IntegerProperty highScoreProperty() {return highScore;}
    public IntegerProperty endRowsProperty() {return endRows;}
    public IntegerProperty mostRowsProperty() {return mostRows;}
    public ObjectProperty<Duration> endTimeProperty() {return endTime;}
    public ObjectProperty<Duration> bestTimeProperty() {return bestTime;}

}
