package com.comp2042.renderers;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.Score;
import javafx.scene.control.Label;


public class StatsRenderer {
    private final Label timeLabel, rowsLabel, currentScoreLabel, highScoreLabel;

    public StatsRenderer(GameController gameController) {
        this.timeLabel = gameController.getGameLabels().get(0);
        this.rowsLabel = gameController.getGameLabels().get(1);
        this.currentScoreLabel = gameController.getGameLabels().get(2);
        this.highScoreLabel = gameController.getGameLabels().get(3);

    }

    public void render(Score score) {
        timeLabel.setVisible(false);
        timeLabel.setManaged(false);
        rowsLabel.setText("Rows cleared: " + score.rowsClearedProperty().getValue());
        currentScoreLabel.setText("Current score: " + score.scoreProperty().getValue());
        highScoreLabel.setText("High score: " + score.highScoreProperty().getValue());
    }

}
