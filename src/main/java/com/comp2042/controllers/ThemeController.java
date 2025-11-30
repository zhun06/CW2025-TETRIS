package com.comp2042.controllers;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;

import com.comp2042.util.Theme;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the theme selection page. Manages previewing available themes,
 * navigation buttons, and theme confirmation.
 *
 * <p>Handles button clicks and keyboard shortcuts for cycling through themes,
 * confirming selection, and updating the UI stylesheet.</p>
 */
public class ThemeController {
    @FXML
    private VBox root;

    @FXML
    private Button prevBtn, nextBtn, confirmBtn;

    @FXML
    private ImageView themePreview;

    private final ArrayList<Image> images = new ArrayList<>();
    private static int currentIndex = 0;

    /**
     * Initializes the theme controller. Sets up focus traversal, clips for rounded
     * corners on the preview, loads theme preview images, and displays the first preview.
     *
     * @throws IOException if image loading fails
     */
    @FXML
    public void initialize() throws IOException {
        // Block focus
        prevBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);
        confirmBtn.setFocusTraversable(false);

        // Clip for rounded corners
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(themePreview.fitWidthProperty());
        clip.heightProperty().bind(themePreview.fitHeightProperty());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        themePreview.setClip(clip);

        // Load images
        images.add(new Image("/images/candyPreview.png"));
        images.add(new Image("/images/minionPreview.png"));
        images.add(new Image("/images/naturePreview.png"));
        images.add(new Image("/images/neonPreview.png"));
        images.add(new Image("/images/oceanPreview.png"));
        images.add(new Image("/images/sunsetPreview.png"));

        // Set the first image
        themePreview.setImage(images.get(currentIndex));
    }

    /**
     * Handles button clicks for navigating previews and confirming theme selection.
     *
     * @param event the button click event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void onButtonClick(ActionEvent event) throws IOException {
        if (event.getSource() == prevBtn) {
            currentIndex--;
            if (currentIndex < 0) currentIndex = images.size() - 1; // wrap around
        }
        else if (event.getSource() == nextBtn) {
            currentIndex++;
            if (currentIndex >= images.size()) currentIndex = 0; // Wrap around
        }
        else if (event.getSource() == confirmBtn) this.confirmBtn();

        // Update themePreview
        themePreview.setImage(images.get(currentIndex));

        // Update css stylesheet
        this.setTheme();
    }

    /**
     * Adds key event handler to support navigation shortcuts and confirmation keys.
     * Includes arrow keys, A/D, space/enter, and fullscreen toggle.
     */
    public void addKeyHandler() {
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case LEFT, A -> {
                    currentIndex--;
                    if (currentIndex < 0) currentIndex = images.size() - 1; // Wrap around
                    themePreview.setImage(images.get(currentIndex)); // Update themePreview
                    this.setTheme(); // Update css

                }
                case RIGHT, D -> {
                    currentIndex++;
                    if (currentIndex >= images.size()) currentIndex = 0; // Wrap around
                    themePreview.setImage(images.get(currentIndex)); // Update themePreview
                    this.setTheme(); // Update css
                }
                case SPACE, ENTER, C, E, Q -> {
                    try {
                        this.confirmBtn();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case F -> stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }

    // Update css
    private void setTheme() {
        switch (currentIndex) { // Update css stylesheet
            case 0 -> SceneManager.setTheme(Theme.CANDY);
            case 1 -> SceneManager.setTheme(Theme.MINION);
            case 2 -> SceneManager.setTheme(Theme.NATURE);
            case 3 -> SceneManager.setTheme(Theme.NEON);
            case 4 -> SceneManager.setTheme(Theme.OCEAN);
            case 5 -> SceneManager.setTheme(Theme.SUNSET);
        }
    }

    @FXML
    private void confirmBtn() throws IOException {ControllerManager.callHomeController();}
}