package com.nessam.client;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import models.User;

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
    private Label error_label;
    @FXML
    private Button toggleNightModeButton;



    public void signUp() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(2);
    }

    public void signIn(ActionEvent actionEvent) {


        if (Username_textfield.getText().isEmpty()) {

            error_label.setText("Please fill all the fields");
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
                    String responseText = response.toString();
                    HelloApplication.setJWT_token(connection.getHeaderField("Authorization"));
                    HelloApplication m = new HelloApplication();
                    url = new URL("http://localhost:8080/users/" + Username_textfield.getText());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();
                    BufferedReader in1 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputline1;
                    StringBuffer response2 = new StringBuffer();
                    while ((inputline1 = in1.readLine()) != null) {
                        response2.append(inputline1);
                    }
                    br.close();
                    responseText = response2.toString();
                    Gson gson = new Gson();
                    User user = gson.fromJson(responseText, User.class);

                    HelloApplication.loggedin_user = user;

                    m.changeScene(6);


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
    @FXML
    public void handleToggleNightMode() {
        HelloApplication.toggleNightMode();
    }
}
