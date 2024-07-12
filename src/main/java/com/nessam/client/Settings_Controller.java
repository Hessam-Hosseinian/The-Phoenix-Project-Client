package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Settings_Controller implements Initializable {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private VBox settingsAccount;

    @FXML
    private SettingsAccount_Controller settingsAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPane("account_preferences_pane.fxml");
//        try {
//            Parent root = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        settingsAccountController = loader.getController();
//        settingsAccountController.setData(userData.getFirstName() + " " + userData.getLastName(), userData.getLocation());
    }

    @FXML
    private void handleAccountPreferences() {
        loadPane("account_preferences_pane.fxml");
    }

    @FXML
    private void handleSignInAndSecurity() {
        loadPane("sign_in_and_security_pane.fxml");
    }

    @FXML
    private void handleVisibility() {
        loadPane("visibility_pane.fxml");
    }

    @FXML
    private void handleNotifications() {
        loadPane("notifications_pane.fxml");
    }

    private void loadPane(String fxmlFile) {
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleToggle(ActionEvent event) {
        ToggleButton toggleButton = (ToggleButton) event.getSource();
        if (toggleButton.isSelected()) {
            toggleButton.setText("Sound");
        } else {
            toggleButton.setText("Silence");
        }
    }


    public void backToFeed(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }

    public void handleAddingPostAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(9);
    }

    public void handleMessagingButton(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(12);
    }

    public void handleProfileAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(8);
    }

    @FXML
    public void handleAddingEducationAction() {
        HelloApplication application = new HelloApplication();
        application.changeScene(14);
    }

    public void handleSettingsAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(10);
    }

    @FXML
    public void handleAddingJob() {
        HelloApplication application = new HelloApplication();

        application.changeScene(15);
    }

}