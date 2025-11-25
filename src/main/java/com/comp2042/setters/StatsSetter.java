package com.comp2042.setters;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.GameResult;
import com.comp2042.logic.data.Score;
import com.comp2042.managers.GameManager;
import com.comp2042.util.GameChoice;
import javafx.scene.control.Label;

import java.time.Duration;


/**Updates the in-game statistics labels (level, score, rows, time, high score) during gameplay.*/
public class StatsSetter {

    private GameChoice gameChoice;
    private final Label levelLabel, timeLabel, rowsLabel, currentScoreLabel, highScoreLabel;

    public StatsSetter(GameController gameController) {
        this.levelLabel = gameController.getGameLabels().get(0);
        this.timeLabel = gameController.getGameLabels().get(1);
        this.rowsLabel = gameController.getGameLabels().get(2);
        this.currentScoreLabel = gameController.getGameLabels().get(3);
        this.highScoreLabel = gameController.getGameLabels().get(4);
    }

    /** Initializes label visibility for a new game depending on the game mode. */
    public void onNewGame() {
        gameChoice = GameManager.getCurrentGameChoice();
        switch (gameChoice) {
            case ZEN -> { showLabel(levelLabel); hideLabel(timeLabel); hideLabel(rowsLabel); showLabel(currentScoreLabel); showLabel(highScoreLabel); }
            case FORTY_LINES, BLITZ -> { hideLabel(levelLabel); showLabel(timeLabel); showLabel(rowsLabel); hideLabel(currentScoreLabel); showLabel(highScoreLabel); }
            case HARDCORE -> { hideLabel(levelLabel); showLabel(timeLabel); hideLabel(rowsLabel); hideLabel(currentScoreLabel); showLabel(highScoreLabel); }
        }
    }

    /** Updates all in-game stats labels during gameplay. */
    public void update(Score score, Duration remainingTime, Duration elapsedTime, GameResult gameResult) {
        setLevel(score);
        setTime(remainingTime, elapsedTime);
        setRows(score);
        setScore(score);
        setHighScore(gameResult);
    }

    private void setLevel(Score score) { levelLabel.setText("Level: " + score.levelProperty().getValue()); }

    private void setTime(Duration remainingTime, Duration elapsedTime) {
        switch (gameChoice) {
            case FORTY_LINES, HARDCORE -> timeLabel.setText("Time: " + formatSeconds(elapsedTime));
            case BLITZ -> timeLabel.setText("Time left: " + formatSeconds(remainingTime));
        }
    }

    private void setRows(Score score) {
        switch (gameChoice) {
            case FORTY_LINES -> rowsLabel.setText("Rows remaining: " + score.rowsRemainingProperty().getValue());
            case BLITZ, HARDCORE -> rowsLabel.setText("Rows cleared: " + score.rowsClearedProperty().getValue());
        }
    }

    private void setScore(Score score) { currentScoreLabel.setText("Current score: " + score.scoreProperty().getValue()); }

    private void setHighScore(GameResult gameResult) {
        switch (gameChoice) {
            case ZEN -> highScoreLabel.setText("High Score: " + gameResult.highScoreProperty().getValue());
            case FORTY_LINES, HARDCORE -> {
                if (gameResult.bestTimeProperty().get() == null) highScoreLabel.setText("Best time: none");
                else highScoreLabel.setText("Best time: " + formatSeconds(gameResult.bestTimeProperty().get()));
            }
            case BLITZ -> highScoreLabel.setText("Most rows: " + gameResult.mostRowsProperty().getValue());
        }
    }

    private void showLabel(Label label) { label.setVisible(true); label.setManaged(true); }
    private void hideLabel(Label label) { label.setVisible(false); label.setManaged(false); }

    private String formatSeconds(Duration d) {
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }
}