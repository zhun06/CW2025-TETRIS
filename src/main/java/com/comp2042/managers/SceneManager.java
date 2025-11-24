package com.comp2042.managers;

import com.comp2042.util.Theme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

// Only ONE scene created:
// If scene don't exist: create and store
// Else: reuse scene, only change root

// Manages stage, scene, root, theme
public class SceneManager {
    private static Parent root;
    private static FXMLLoader loader;
    private static Stage stage;
    private static Scene scene;
    private static Theme currentTheme = Theme.CANDY; // Default
    private static String fileName;

    // We don't want to instantiate this utility class
    private SceneManager() {}

    // Initialize scene (ONCE)
    public static void initScene() {
        if (scene == null) {
            root = new Pane(); // Blank root
            scene = new Scene(root);
            stage.setScene(scene);
            setSceneStyle();
            stage.setMinWidth(1000);
            stage.setMinHeight(700);
            stage.show();
        }
    }

    // Read FXML
    public static void readFXML(String fxmlName) throws IOException {
        loader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxmlName + ".fxml"));
        root = loader.load();
    }

    // Change root
    public static void setRoot(Parent root) {
        scene.setRoot(root);
        if (!stage.isFullScreen()) {
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    private static void setSceneStyle() {
        scene.getStylesheets().add(SceneManager.class.getResource("/css/base.css").toExternalForm());
        SceneManager.setTheme(currentTheme);
    }

    public static void setTheme(Theme theme) {
        // Remove any existing theme stylesheet
        scene.getStylesheets().removeIf(s -> s.contains("/css/themes/"));
        currentTheme = theme;
        switch(currentTheme) {
            case CANDY -> fileName = "candy";
            case MINION -> fileName = "minion";
            case NATURE -> fileName = "nature";
            case NEON -> fileName = "neon";
            case OCEAN -> fileName = "ocean";
            case SUNSET ->  fileName = "sunset";
        }
        scene.getStylesheets().add(SceneManager.class.getResource("/css/themes/" + fileName + ".css").toExternalForm());
    }

    // Setters
    public static void setStage(Stage s) {
        stage = s;
    }
    // Getters
    public static Parent getRoot() {return root;}
    public static FXMLLoader getLoader() {return loader;}
    public static Scene getScene() {return scene;}
    public static Stage getStage() {return stage;}
    public static Theme getTheme() {return currentTheme;}
}


