package com.comp2042.controllers;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

// Controls theme-switching page
public class ThemeController {
    @FXML
    private Button prevBtn, nextBtn, confirmBtn;

    @FXML
    private ImageView themePreview;

    private final ArrayList<Image> images = new ArrayList<>();
    private static int currentIndex = 0;

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
        images.add(new Image("/images/theme1.jpg"));
        images.add(new Image("/images/theme2.jpg"));
        images.add(new Image("/images/theme3.jpg"));

        // Set the first image
        themePreview.setImage(images.get(currentIndex));
    }

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

    public void addKeyHandler() throws IOException {
        Parent root = ControllerManager.getThemeRoot();
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
                case SPACE, ENTER, C -> {
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
            case 0 -> SceneManager.setTheme("theme1");
            case 1 -> SceneManager.setTheme("theme2");
            case 2 -> SceneManager.setTheme("theme3");
        }
    }

    @FXML
    private void confirmBtn() throws IOException {
        ControllerManager.callHomeController();
    }
}

