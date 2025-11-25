package com.comp2042.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.comp2042.managers.ControllerManager;
import static com.comp2042.util.GameChoice.*;

import com.comp2042.managers.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Controller for the landing/home page. Manages the game mode
 * preview, navigation buttons, game mode selection, start button,
 * leaderboard access, and quit functionality.
 *
 * <p>Handles button clicks and keyboard shortcuts for navigation,
 * game mode selection, and starting a game.</p>
 */
public class HomeController {
    @FXML
    private StackPane root;

    @FXML
    private Button prevBtn, nextBtn, themeBtn, startBtn, leaderBoardBtn, quitBtn;

    @FXML
    private ImageView gamePreview;

    private final ArrayList<Image> images = new ArrayList<>();
    private static int currentIndex = 0;

    /**
     * Initializes the home page controller. Sets up focus traversal,
     * clips for rounded corners on the game preview, loads game preview
     * images, and displays the first preview.
     */
    @FXML
    public void initialize() {
        // Block focus
        prevBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);
        themeBtn.setFocusTraversable(false);
        startBtn.setFocusTraversable(false);
        quitBtn.setFocusTraversable(false);

        // Clip for rounded corners
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(gamePreview.fitWidthProperty());
        clip.heightProperty().bind(gamePreview.fitHeightProperty());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        gamePreview.setClip(clip);

        // Load images
        images.add(new Image("/images/zen.png"));
        images.add(new Image("/images/fortyLines.png"));
        images.add(new Image("/images/blitz.png"));
        images.add(new Image("/images/hardcore.png"));

        // Set the first image
        gamePreview.setImage(images.get(currentIndex));
    }

    /**
     * Handles previous/next game preview button clicks.
     *
     * @param event the button click event
     */
    @FXML
    private void handleGamePreview(ActionEvent event) {
        if (event.getSource() == prevBtn) {
            currentIndex--;
            if (currentIndex < 0) currentIndex = images.size() - 1; // wrap around
        } else if (event.getSource() == nextBtn) {
            currentIndex++;
            if (currentIndex >= images.size()) currentIndex = 0; // wrap around
        }

        // Update gamePreview
        gamePreview.setImage(images.get(currentIndex));
    }

    /**
     * Handles general home page button clicks (theme, leaderboard, start, quit).
     *
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void onButtonClick(ActionEvent event) throws IOException {
        if (event.getSource() == themeBtn) {
            this.themeBtn();
        }
        if (event.getSource() == leaderBoardBtn) {
            this.leaderBoardBtn();
        }
        if (event.getSource() == startBtn) {
            this.startBtn();
        }
        if (event.getSource() == quitBtn) {
            this.quitBtn();
        }
    }

    /**
     * Adds key event handler to the root pane for navigation shortcuts
     * (LEFT/RIGHT for preview, SPACE/ENTER to start, C/T for theme, etc.).
     */
    public void addKeyHandler() {
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case LEFT, A -> {
                    currentIndex--;
                    if (currentIndex < 0) currentIndex = images.size() - 1; // wrap around
                    gamePreview.setImage(images.get(currentIndex)); // Update themePreview
                }
                case RIGHT, D -> {
                    currentIndex++;
                    if (currentIndex >= images.size()) currentIndex = 0; // wrap around
                    gamePreview.setImage(images.get(currentIndex)); // Update themePreview
                }
                case C, T -> {
                    try {
                        this.themeBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case L, B -> {
                    try {
                        this.leaderBoardBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case SPACE, ENTER, S, P -> {
                    try {
                        this.startBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case E, Q -> this.quitBtn();

                case F -> stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }


    // Private helper methods for button actions
    private void themeBtn() throws IOException {
        ControllerManager.callThemeController();
    }

    private void startBtn() throws IOException {
        switch (currentIndex) {
            case 0 -> ControllerManager.callGameController(ZEN);
            case 1 -> ControllerManager.callGameController(FORTY_LINES);
            case 2 -> ControllerManager.callGameController(BLITZ);
            case 3 -> ControllerManager.callGameController(HARDCORE);
        }
    }

    public void leaderBoardBtn() throws IOException {ControllerManager.callLeaderBoardController();}

    private void quitBtn() {Platform.exit();}
}
