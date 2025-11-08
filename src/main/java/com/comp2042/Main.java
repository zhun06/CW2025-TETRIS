package com.comp2042;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);
        ControllerManager.callHomeController();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
