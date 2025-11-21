package com.comp2042.controllers;

import com.comp2042.highScore.ScoreRecord;
import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;
import com.comp2042.setters.LeaderBoardSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Leader board page
public class LeaderBoardController {
    @FXML
    private VBox root;

    @FXML
    private TableView<ScoreRecord> leaderBoardTable;

    @FXML
    private TableColumn<ScoreRecord, String> modeColumn, timeColumn, scoreColumn, rowsColumn;

    @FXML
    private Button homeBtn, themeBtn;

    @FXML
    private void onButtonClick(ActionEvent event) throws IOException {
        if (event.getSource() == homeBtn) {
            this.homeBtn();
        }
        if (event.getSource() == themeBtn) {
            this.themeBtn();
        }
    }

    // Update leaderboard using data from csv
    public void refresh() {
        LeaderBoardSetter leaderBoardSetter = new LeaderBoardSetter(this);
        leaderBoardSetter.update();
    }

    public void addKeyHandler() {
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case C, T -> {
                    try {
                        this.themeBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case H, E, Q, SPACE, ENTER -> {
                    try {
                        this.homeBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case F -> stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }

    private void homeBtn() throws IOException {ControllerManager.callHomeController();}
    private void themeBtn() throws IOException {ControllerManager.callThemeController();}

    // Getters
    public TableView<ScoreRecord> getLeaderBoardTable() {return leaderBoardTable;}
    public List<TableColumn<ScoreRecord, String>> getLeaderBoardColumns() {return new ArrayList<>(Arrays.asList(modeColumn, timeColumn, scoreColumn, rowsColumn));}
}
