package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login_Controller {

    @FXML
    private TextField Username_textfield;

    @FXML
    private PasswordField Password_textfield;

    @FXML
    private Button signIn_button;

    @FXML
    private Label user_error_label;
    @FXML
    private Label pass_error_label;
    @FXML
    private Label error_label;

    public void signUp() throws IOException {
        HelloApplication application= new HelloApplication();
        application.changeScene(2);
    }

    public void signIn(ActionEvent actionEvent) {


        if (Username_textfield.getText().isEmpty()) {
            user_error_label.setText("Please enter username");
            user_error_label.setTextFill(Color.RED);

        }
        if (Password_textfield.getText().isEmpty()) {
            pass_error_label.setText("Please enter password");
            pass_error_label.setTextFill(Color.RED);

        } else {
            try {
                URL url = new URL("http://localhost:8080/req/" + Username_textfield.getText() + "/" + Password_textfield.getText());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                System.out.println(HttpURLConnection.HTTP_OK + ": " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
                    // Handle success case
                    error_label.setText("Logged in successfully");
                    error_label.setTextFill(Color.GREEN);
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    error_label.setText("Incorrect username or password");
                    error_label.setTextFill(Color.RED);
                } else {
                    error_label.setText("Login failed: " + responseCode);
                    error_label.setTextFill(Color.RED);
                }
            } catch (MalformedURLException e) {
                error_label.setText("Invalid URL format");
                error_label.setTextFill(Color.RED);
            } catch (IOException e) {
                error_label.setText("Connection failed: " + e.getMessage());
                error_label.setTextFill(Color.RED);
            }
        }
    }
    public void _button_entered() {
        signIn_button.setStyle("-fx-background-color: #0a66c2;");

    }
    public void _button_exit() {
        signIn_button.setStyle("-fx-background-color: #0598ff;");
    }
}
