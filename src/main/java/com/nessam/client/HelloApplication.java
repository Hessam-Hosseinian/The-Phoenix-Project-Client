package com.nessam.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primryStage) throws IOException {
        stage = primryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUp1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primryStage.setTitle("Hello!");
        stage.setScene(scene);
        primryStage.show();
    }

    public void changeScene(int sceneNum) throws IOException {
        if (sceneNum == 1) {
            stage.setScene(new Scene((new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"))).load()));
        }
        if (sceneNum == 2) {
            stage.setScene(new Scene((new FXMLLoader(HelloApplication.class.getResource("SignUp1.fxml"))).load()));
        }
        if (sceneNum == 3) {
            stage.setScene(new Scene((new FXMLLoader(HelloApplication.class.getResource("SignUp2.fxml"))).load()));
        }
        if (sceneNum == 4) {
            stage.setScene(new Scene((new FXMLLoader(HelloApplication.class.getResource("SignUp3.fxml"))).load()));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}