package com.nessam.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;


import java.io.IOException;

public class HelloApplication extends Application {
    private static String JWT_token;
    public static User loggedin_user = new User();
    private static Stage stage;
    private static boolean isNightMode = false;

    @Override
    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        changeScene(4);
        primaryStage.show();
    }

    public static void toggleNightMode() {
        isNightMode = !isNightMode;
        Scene currentScene = stage.getScene();
        HelloApplication application = new HelloApplication();
        application.applyStylesheet(currentScene);
    }

    private String getFxmlFileForCurrentScene(Scene currentScene) {

        if (currentScene.getRoot().getId().equals("LoginPage")) {
            return "LoginPage.fxml";
        }

        return "LoginPage.fxml";
    }

    private void applyStylesheet(Scene scene) {
        scene.getStylesheets().clear();
        if (isNightMode) {

            scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        } else {

            scene.getStylesheets().add(getClass().getResource("DarkStyle.css").toExternalForm());
        }
    }

    public void changeScene(int sceneNum) throws IOException {
        String fxmlFile = "";
        switch (sceneNum) {
            case 1:
                fxmlFile = "LoginPage.fxml";
                break;
            case 2:
                fxmlFile = "SignUp1.fxml";
                break;
            case 3:
                fxmlFile = "SignUp2.fxml";
                break;
            case 4:
                fxmlFile = "SignUp3.fxml";
                break;
            case 5:
                fxmlFile = "SignUp4.fxml";
                break;
            case 6:
                fxmlFile = "feed.fxml";
                break;
            case 7:
                fxmlFile = "Post.fxml";
                break;
        }
        if (!fxmlFile.isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            applyStylesheet(scene);
            stage.setScene(scene);
        }
    }

    public static String getJWT_token() {
        return JWT_token;
    }

    public static void setJWT_token(String JWT_token) {
        HelloApplication.JWT_token = JWT_token;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
