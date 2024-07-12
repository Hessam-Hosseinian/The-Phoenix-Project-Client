package com.nessam.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsNotifications_Controller implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxConnect;
    @FXML
    private ChoiceBox<String> choiceBoxPost;
    @FXML
    private ChoiceBox<String> choiceBoxMessage;
    @FXML
    private ChoiceBox<String> choiceBoxGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxGroup.getItems().addAll("Silence", "Sound");
        choiceBoxConnect.getItems().addAll("Silence", "Sound");
        choiceBoxMessage.getItems().addAll("Silence", "Sound");
        choiceBoxPost.getItems().addAll("Silence", "Sound");
    }
}
