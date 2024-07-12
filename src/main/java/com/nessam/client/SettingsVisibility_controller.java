package com.nessam.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsVisibility_controller implements Initializable {

    @FXML
    private ChoiceBox<String> VisibilityBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VisibilityBox.getItems().addAll("Private Mode", "Your Name and Headline");
    }
}
